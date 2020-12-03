package pl.coderslab.blinddate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.blinddate.dto.UserDto;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.exception.DuplicateEmailException;
import pl.coderslab.blinddate.repository.UserRepository;
import pl.coderslab.blinddate.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


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
        User userEntity = UserMapper.toEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(userEntity);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public void checkEmailDuplicate(UserDto userDto) {
        Optional<User> userById = userRepository.findByEmail(userDto.getEmail());
        userById.ifPresent(u -> {
            throw new DuplicateEmailException();
        });
    }

//    @Override
//    public boolean userLoginVerification(LoginDto loginDto){
//        if(userLoginExist(loginDto)){
//            String passwordFromDb = userRepository.findPasswordForUser(loginDto.getEmail());
//            return loginDto.getPassword().equals(passwordFromDb);
//        }
//        return false;
//
//    }
//
//    @Override
//    public boolean userLoginExist(LoginDto loginDto) {
//        User user = userRepository.findFirstByEmail(loginDto.getEmail());
//        return !userRepository.existsById(user.getId());
//    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }
}
