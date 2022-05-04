package pl.zukowski.jwtauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zukowski.jwtauth.entity.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName (String name);
}
