package pl.coderslab.blinddate.entity;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private int age;
    @NotNull
    private char gender;
    @NotNull
    private char orientation;
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne
    @JoinColumn(name = "users_id")
    private User user;

    public UserDetails(Long id, @NotNull String name, @NotNull int age, @NotNull char gender, @NotNull char orientation, String description) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.orientation = orientation;
        this.description = description;
    }
}
