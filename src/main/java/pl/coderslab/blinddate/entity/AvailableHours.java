package pl.coderslab.blinddate.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Comparator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private int dayOfWeek;
    private int h;

    public AvailableHours(User user, int dayOfWeek, int hour) {
        this.user = user;
        this.dayOfWeek = dayOfWeek;
        this.h = h;
    }
}
