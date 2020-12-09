package pl.coderslab.blinddate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.blinddate.dto.UserDto;
import pl.coderslab.blinddate.entity.*;
import pl.coderslab.blinddate.exception.DuplicateEmailException;
import pl.coderslab.blinddate.repository.LikesRepository;
import pl.coderslab.blinddate.repository.MatchesRepository;
import pl.coderslab.blinddate.repository.RejectsRepository;
import pl.coderslab.blinddate.repository.UserRepository;
import pl.coderslab.blinddate.mapper.UserMapper;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageService messageService;
    private final DateService dateService;
    private final RejectsRepository rejectsRepository;
    private final LikesRepository likesRepository;
    private final MatchesRepository matchesRepository;


    @Override
    public List<UserDto> findAll() {
        log.warn("Getting all users");
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto save(UserDto userDto) {
        checkEmailDuplicate(userDto);
        return mapAndSaveUser(userDto);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        log.warn("Deleting user "+ id);
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

    @Override
    public UserDto mapAndSaveUser(UserDto userDto) {
        log.warn("Saving new user");
        User user = UserMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(Collections.singleton("ROLE_USER"));
        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public void checkEmailDuplicate(UserDto userDto) {
        Optional<User> userById = userRepository.findByEmail(userDto.getEmail());
        userById.ifPresent(u -> {
            throw new DuplicateEmailException();
        });
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public List<User> findAllByCity(String city) {
        return userRepository.findAllByCity(city);
    }

    @Override
    public List<User> findAvailableForUser() {
        User loggedUser = getLogged();
        log.warn("Getting available users for user "+loggedUser.getId());
        List<User> allUsersInSameCity = findAllByCity(loggedUser.getCity());
        List<User> likedByUser = findLikedByUser(loggedUser);
        List<User> rejectedByUser = findRejectedByUser(loggedUser);
        List<User> availableUsers = new ArrayList<>();
        for(User u : allUsersInSameCity){
            if(!likedByUser.contains(u)&&!rejectedByUser.contains(u)){
                availableUsers.add(u);
            }
        }
        availableUsers.remove(loggedUser);
        return availableUsers;
    }

    @Override
    public List<User> findLikedByUser(User user) {
        log.warn("Getting liked by user "+user.getId());
        List<Likes> likedByUser = userRepository.findLiked(user.getId());
        List<User> likedUsers = new ArrayList<>();
        for(Likes l : likedByUser){
            likedUsers.add(l.getLiked());
        }
        return likedUsers;
    }

    @Override
    public List<User> findRejectedByUser(User user) {
        log.warn("Getting rejected by user "+user.getId());
        List<Rejects> likedByUser = userRepository.findRejected(user.getId());
        List<User> rejectedUsers = new ArrayList<>();
        for(Rejects r : likedByUser){
            rejectedUsers.add(r.getRejected());
        }
        return rejectedUsers;
    }

    @Override
    public String getLoggedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Override
    public User getLogged() {
        return getUserByEmail(getLoggedEmail());
    }


    @Override
    public void likeUser(Long id) {
        User loggedUser = getLogged();
        log.warn("User "+loggedUser.getId()+" likes user "+id);
        User liked = userRepository.getOne(id);
        Likes like = new Likes();
        like.setLiked(liked);
        like.setLiking(loggedUser);
        likesRepository.save(like);
        if(checkIfLiked(id)){
            matchUsers(id);
        }
    }

    @Override
    public void rejectUser(Long id) {
        User loggedUser = getLogged();
        log.warn("User "+loggedUser.getId()+" rejects user "+id);
        User rejected = userRepository.getOne(id);
        Rejects reject = new Rejects();
        reject.setRejected(rejected);
        reject.setRejecting(loggedUser);
        rejectsRepository.save(reject);
    }

    @Override
    public boolean checkIfLiked(Long likedId) {
        User loggedUser = getLogged();
        List<Likes> likedByUser = userRepository.findLiked(likedId);
        log.warn("Checking if user liked back");
        for(Likes l : likedByUser){
            if(l.getLiked().equals(loggedUser)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void matchUsers(Long id) {
        User loggedUser = getLogged();
        log.warn("Creatiing a match between "+loggedUser.getId()+" and "+id);
        User matched = userRepository.getOne(id);
        Matches match = new Matches();
        match.setUser1(loggedUser);
        match.setUser2(matched);
        matchesRepository.save(match);
        dateService.createNewDate(match);
    }




}
