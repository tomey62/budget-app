package pl.zukowski.jwtauth.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.zukowski.jwtauth.dto.SumDto;
import pl.zukowski.jwtauth.entity.Card;
import pl.zukowski.jwtauth.entity.Transaction;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByCard(Pageable page, Card card);
    List<Transaction> findByDateCreatedBetweenAndCard(Pageable page ,String start, String end, Card card);
    @Query(nativeQuery = true)
    List<SumDto> sumByDate (String date, Card card);
}
