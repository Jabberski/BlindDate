package pl.coderslab.blinddate.mapper;

import pl.coderslab.blinddate.dto.UserDto;
import pl.coderslab.blinddate.entity.User;


public class UserMapper {
    public static User toEntity(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setCity(userDto.getCity());
        return user;
    }
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        user.setCity(userDto.getCity());
        return userDto;
    }
}
