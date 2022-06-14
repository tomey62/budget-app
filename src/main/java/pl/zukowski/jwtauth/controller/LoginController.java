package pl.zukowski.jwtauth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zukowski.jwtauth.dto.LoginCredentials;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/login")
    public void authenticate(@Valid @RequestBody LoginCredentials loginCredentials)
    {

    }

}
