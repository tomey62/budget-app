package pl.zukowski.jwtauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zukowski.jwtauth.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {

}
