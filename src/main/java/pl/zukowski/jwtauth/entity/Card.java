package pl.zukowski.jwtauth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;
    @NotNull
    private Long cardNumber;
    private String name;
    private String type;
    private Long balance;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @OneToMany(mappedBy="card", cascade = CascadeType.REMOVE)
    private Collection<Transaction> transactions = new ArrayList<>();
}
