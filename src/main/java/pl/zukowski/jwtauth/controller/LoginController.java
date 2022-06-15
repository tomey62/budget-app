package pl.zukowski.jwtauth.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.zukowski.jwtauth.dto.LoginCredentials;
import pl.zukowski.jwtauth.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {
    private final UserService userService;

    @PostMapping("/login")
    public void authenticate(@Valid @RequestBody LoginCredentials loginCredentials) {

    }

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);

    }

}
