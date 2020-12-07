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

    @OneToMany(mappedBy = "rejecting")
    private List<Rejects> rejects = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @Column(name = "role")
    private Set<String> roles;


}
