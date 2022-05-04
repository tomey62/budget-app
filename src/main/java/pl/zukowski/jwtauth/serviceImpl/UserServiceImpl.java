package pl.zukowski.jwtauth.serviceImpl;

import org.springframework.stereotype.Service;
import pl.zukowski.jwtauth.entity.Role;
import pl.zukowski.jwtauth.entity.User;
import pl.zukowski.jwtauth.repository.RoleRepository;
import pl.zukowski.jwtauth.repository.UserRepository;
import pl.zukowski.jwtauth.service.UserService;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public User saveUser(User user) {
      return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String login, String roleName) {
        User user = userRepo.findByLogin(login);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String login) {
        return userRepo.findByLogin(login);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }
}
