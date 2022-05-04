package pl.zukowski.jwtauth.service;

import pl.zukowski.jwtauth.entity.Role;
import pl.zukowski.jwtauth.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String login, String roleName);
    User getUser(String login);
    List<User> getUsers();
}
