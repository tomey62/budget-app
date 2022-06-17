package pl.zukowski.jwtauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zukowski.jwtauth.entity.Card;
import pl.zukowski.jwtauth.entity.User;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByCardNumber(Long cardNumber);
    List<Card> findByUser(User user);
    Card findByUserAndCardNumber(User user, Long cardNumber);

}
