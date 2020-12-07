package pl.coderslab.blinddate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.blinddate.dto.UserDto;
import pl.coderslab.blinddate.entity.AvailableHours;
import pl.coderslab.blinddate.entity.Likes;
import pl.coderslab.blinddate.entity.Rejects;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.exception.DuplicateEmailException;
import pl.coderslab.blinddate.repository.UserRepository;
import pl.coderslab.blinddate.mapper.UserMapper;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;
    private final MessageService messageService;
    private final DateService dateService;


    @Override
    public List<UserDto> findAll() {
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
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

    @Override
    public UserDto mapAndSaveUser(UserDto userDto) {
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
        User loggedUser = getUserByEmail(getLoggedEmail());
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
        List<Likes> likedByUser = userRepository.findLiked(user.getId());
        List<User> likedUsers = new ArrayList<>();
        for(Likes l : likedByUser){
            likedUsers.add(getById(l.getLikedId()).orElse(null));
        }
        return likedUsers;
    }

    @Override
    public List<User> findRejectedByUser(User user) {
        List<Rejects> likedByUser = userRepository.findRejected(user.getId());
        List<User> rejectedUsers = new ArrayList<>();
        for(Rejects r : likedByUser){
            rejectedUsers.add(getById(r.getRejectedId()).orElse(null));
        }
        return rejectedUsers;
    }

    @Override
    public String getLoggedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.warn(authentication.getName());
        return authentication.getName();
    }

    @Override
    @Transactional
    public void likeUser(Long id) {
        User loggedUser = getUserByEmail(getLoggedEmail());
        entityManager.createNativeQuery("INSERT INTO likes (liked_id, liking_id) VALUES (?,?)")
                .setParameter(1, id)
                .setParameter(2, loggedUser.getId())
                .executeUpdate();
        if(checkIfLiked(id)){
            matchUsers(id);
        }
    }

    @Override
   @Transactional
    public void rejectUser(Long id) {
        User loggedUser = getUserByEmail(getLoggedEmail());
        entityManager.createNativeQuery("INSERT INTO rejects (rejected_id, rejecting_id) VALUES (?,?)")
                .setParameter(1, id)
                .setParameter(2, loggedUser.getId())
                .executeUpdate();
    }

    @Override
    public boolean checkIfLiked(Long likedId) {
        User loggedUser = getUserByEmail(getLoggedEmail());
        List<Likes> likedByUser = userRepository.findLiked(likedId);
        for(Likes l : likedByUser){
            if(l.getLikedId().equals(loggedUser.getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void matchUsers(Long id) {
        User loggedUser = getUserByEmail(getLoggedEmail());
        entityManager.createNativeQuery("INSERT INTO matches (user1id, user2id) VALUES (?,?)")
                .setParameter(1, id)
                .setParameter(2, loggedUser.getId())
                .executeUpdate();
        dateService.createNewDate(id, loggedUser.getId());
    }




}
