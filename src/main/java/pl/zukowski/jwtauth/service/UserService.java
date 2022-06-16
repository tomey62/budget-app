package pl.zukowski.jwtauth.service;

import pl.zukowski.jwtauth.dto.UserDto;
import pl.zukowski.jwtauth.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {
    void saveUser(UserDto user);
    User getUser(String login);
    List<UserDto> getUsers();
    UserDto convertEntityToDto(User user);
    void resetPassword(String email);
    void changePassword(HttpServletRequest request, String password);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
