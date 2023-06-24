package pl.zukowski.jwtauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zukowski.jwtauth.entity.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

}
