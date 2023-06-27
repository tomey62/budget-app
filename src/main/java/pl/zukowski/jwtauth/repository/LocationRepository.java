package pl.zukowski.jwtauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.zukowski.jwtauth.entity.Location;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
    List<Location> searchByCountry(String country);
    List<Location> searchByCity(String city);
    List<Location> searchByName(String name);
    @Query(value = "SELECT l.* FROM locations l JOIN favourite f ON l.id = f.location_id WHERE f.user_id = :userId", nativeQuery = true)
    List<Location> findFavoriteLocationsByUserId(@Param("userId") Long userId);
}
