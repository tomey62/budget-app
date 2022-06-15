package pl.zukowski.jwtauth.service;

import pl.zukowski.jwtauth.dto.UserDto;
import pl.zukowski.jwtauth.entity.Role;
import pl.zukowski.jwtauth.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {
    User saveUser(UserDto user);
    Role saveRole(Role role);
    void addRoleToUser(String login, String roleName);
    User getUser(String login);
    List<UserDto> getUsers();
    UserDto convertEntityToDto(User user);
    User resetPassword(String email);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
