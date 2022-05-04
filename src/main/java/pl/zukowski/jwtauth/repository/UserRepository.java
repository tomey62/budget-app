package pl.zukowski.jwtauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zukowski.jwtauth.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin (String login);
}
