package pl.coderslab.blinddate;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BlinddateApplication {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BlinddateApplication.class, args);

    }


    @Bean
    public CommandLineRunner start() {
        return args -> {
            User user = User.builder()
                    .email("user")
                    .password(passwordEncoder.encode("user"))
                    .roles(Collections.singleton("ROLE_USER"))
                    .city("Wrocław")
                    .build();
            User admin = User.builder()
                    .email("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(Collections.singleton("ROLE_ADMIN"))
                    .city("Warszawa")
                    .build();
            User user1 = User.builder()
                    .email("user1")
                    .password(passwordEncoder.encode("user"))
                    .roles(Collections.singleton("ROLE_USER"))
                    .city("Wrocław")
                    .build();
            User user2 = User.builder()
                    .email("user2")
                    .password(passwordEncoder.encode("user"))
                    .roles(Collections.singleton("ROLE_USER"))
                    .city("Wrocław")
                    .build();
            User user3 = User.builder()
                    .email("user3")
                    .password(passwordEncoder.encode("user"))
                    .roles(Collections.singleton("ROLE_USER"))
                    .city("Wrocław")
                    .build();
            repository.saveAll(List.of(user, admin, user3, user2, user1));
        };
    }

}
