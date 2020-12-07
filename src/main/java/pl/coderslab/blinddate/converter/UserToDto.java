package pl.coderslab.blinddate.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import pl.coderslab.blinddate.dto.UserDto;
import pl.coderslab.blinddate.entity.User;

public class UserToDto implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User user) {
        UserDto userDto;
        return null;
    }

    @Override
    public <U> Converter<User, U> andThen(Converter<? super UserDto, ? extends U> after) {
        return null;
    }
}
