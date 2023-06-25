package pl.zukowski.jwtauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.zukowski.jwtauth.entity.Score;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findByLocationId(Long locationId);

    @Query("SELECT AVG(s.rating) FROM Score s WHERE s.location.id = :locationId")
    Double getAverageRatingForLocation(@Param("locationId") Long locationId);
}
