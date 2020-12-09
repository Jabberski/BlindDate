package pl.coderslab.blinddate.service;

import pl.coderslab.blinddate.dto.UserDto;
import pl.coderslab.blinddate.entity.AvailableHours;
import pl.coderslab.blinddate.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();
    UserDto save(UserDto userDto);
    Optional<User> getById(Long id);
    boolean deleteById(Long id);
    UserDto mapAndSaveUser(UserDto userDto);
    void checkEmailDuplicate(UserDto userDto);
    User getUserByEmail(String email);
    List<User> findAllByCity(String city);
    List<User> findAvailableForUser();
    List<User> findLikedByUser(User user);
    List<User> findRejectedByUser(User user);
    String getLoggedEmail();
    User getLogged();
    void likeUser(Long id);
    void rejectUser(Long id);
    boolean checkIfLiked(Long likedId);
    void matchUsers(Long id);


}
