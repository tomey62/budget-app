package pl.zukowski.jwtauth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private float rating;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;
}
