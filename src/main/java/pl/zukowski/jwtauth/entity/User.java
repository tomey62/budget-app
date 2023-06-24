package pl.zukowski.jwtauth.entity;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import static javax.persistence.FetchType.EAGER;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private String password;
    private String email;
    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "Favourite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id"))
    private Collection<Location> locations = new ArrayList<>();


}
