package pl.coderslab.blinddate.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String type;
    @OneToMany(mappedBy = "type")
    private List<Places> places;
    @ManyToOne
    private UserDetails userDetails;

    @Override
    public String toString() {
        return "PlaceTypes{" +
                "type='" + type + '\'' +
                '}';
    }
}
