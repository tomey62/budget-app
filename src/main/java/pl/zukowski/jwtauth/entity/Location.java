package pl.zukowski.jwtauth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String country;
    private String city;
    private String category;
    private boolean isFavourite = false;
    @Lob
    private byte[] photo;
    @OneToMany(mappedBy="location", cascade = CascadeType.REMOVE)
    private Collection<Score> scores = new ArrayList<>();

}
