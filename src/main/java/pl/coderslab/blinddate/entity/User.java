package pl.coderslab.blinddate.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(of = "email")
@ToString(exclude = "password")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String city;
    private boolean isVerified=false;
    private boolean isAdmin=false;
    private boolean hasDetails=false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserDetails userDetails;

    @OneToMany(mappedBy = "liking")
    private List<Likes> likes = new ArrayList<>();
    @OneToMany(mappedBy = "liked")
    private List<Likes> liked = new ArrayList<>();

    @OneToMany(mappedBy = "rejecting")
    private List<Rejects> rejected = new ArrayList<>();
    @OneToMany(mappedBy = "rejected")
    private List<Rejects> wasRejected = new ArrayList<>();

    @OneToMany(mappedBy = "user1")
    private List<Matches> matches1 = new ArrayList<>();
    @OneToMany(mappedBy = "user2")
    private List<Matches> matches2 = new ArrayList<>();


    @OneToMany(mappedBy = "user1")
    private List<Dates> dates1 = new ArrayList<>();
    @OneToMany(mappedBy = "user2")
    private List<Dates> dates2 = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<AvailableHours> availableHours = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Messages> messages = new ArrayList<>();



    @ElementCollection
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @Column(name = "role")
    private Set<String> roles;


}
