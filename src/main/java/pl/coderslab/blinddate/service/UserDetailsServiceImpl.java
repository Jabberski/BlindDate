package pl.coderslab.blinddate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.entity.UserDetails;
import pl.coderslab.blinddate.repository.UserDetailsRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{
    private final UserDetailsRepository userDetailsRepository;

    @Override
    public void updateUserDetails(UserDetails user) {
        log.warn("updating details for user "+user.getId());
        userDetailsRepository.updateUserDetails(user.getAge(), user.getDescription(), user.getGender(),
                user.getName(), user.getOrientation(), user.getUser());
    }
}
