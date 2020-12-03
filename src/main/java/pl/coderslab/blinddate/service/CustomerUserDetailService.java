package pl.coderslab.blinddate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.blinddate.repository.UserRepository;

import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerUserDetailService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Searching for user by email '{}'", email);

        if (!repository.existsByEmail(email)) {
            throw new UsernameNotFoundException(String.format("Email %s not found", email));
        }
        pl.coderslab.blinddate.entity.User user = repository.getByEmail(email);
        return new User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
