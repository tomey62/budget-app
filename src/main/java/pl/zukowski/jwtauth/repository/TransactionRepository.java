package pl.zukowski.jwtauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zukowski.jwtauth.entity.Card;
import pl.zukowski.jwtauth.entity.Transaction;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByCard(Card card);
    List<Transaction> findByDateCreatedBetweenAndCard(String start, String end, Card card);
}
