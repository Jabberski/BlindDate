package pl.coderslab.blinddate;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.coderslab.blinddate.entity.PlaceTypes;
import pl.coderslab.blinddate.entity.Places;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.entity.UserDetails;
import pl.coderslab.blinddate.repository.PlacesRepository;
import pl.coderslab.blinddate.repository.PlacesTypesRepository;
import pl.coderslab.blinddate.repository.UserDetailsRepository;
import pl.coderslab.blinddate.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BlinddateApplication {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final PlacesTypesRepository placesTypesRepository;
    private final PlacesRepository placesRepository;
    private final UserDetailsRepository userDetailsRepository;

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
                    .withDetails(true)
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
                    .withDetails(true)
                    .build();

            repository.saveAll(List.of(user, admin, user3, user2, user1));

            PlaceTypes placeType1 = PlaceTypes.builder()
                    .type("Pub")
                    .build();
            PlaceTypes placeType2 = PlaceTypes.builder()
                    .type("Cinema")
                    .build();
            PlaceTypes placeType3 = PlaceTypes.builder()
                    .type("Restaurant")
                    .build();
            placesTypesRepository.saveAll(List.of(placeType1, placeType2, placeType3));


            Places place1 = Places.builder()
                    .city("Wrocław")
                    .address("Szczytnicka 52")
                    .name("Cybermachina")
                    .type(placesTypesRepository.getOne(1L))
                    .build();
            Places place2 = Places.builder()
                    .city("Wrocław")
                    .address("Rynek 13/14")
                    .name("Pijalnia wódki i piwa")
                    .type(placesTypesRepository.getOne(1L))
                    .build();
            Places place3 = Places.builder()
                    .city("Wrocław")
                    .address("Plac Grunwaldzki 18")
                    .name("Remont Bar")
                    .type(placesTypesRepository.getOne(1L))
                    .build();
            Places place4 = Places.builder()
                    .city("Wrocław")
                    .address("Podwale 37/38")
                    .name("Wędrówki Pub")
                    .type(placesTypesRepository.getOne(1L))
                    .build();

            placesRepository.saveAll(List.of(place1, place2, place3, place4));

            UserDetails details = UserDetails.builder()
                    .user(user)
                    .age(20)
                    .gender('M')
                    .name("Jan")
                    .orientation('S')
                    .build();
            UserDetails details3 = UserDetails.builder()
                    .user(user3)
                    .age(20)
                    .gender('F')
                    .name("Kasia")
                    .orientation('S')
                    .build();
            userDetailsRepository.saveAll(List.of(details, details3));
        };
    }

}
