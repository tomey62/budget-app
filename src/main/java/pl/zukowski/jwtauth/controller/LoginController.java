package pl.zukowski.jwtauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zukowski.jwtauth.dto.UserDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    /*@PostMapping("/signin")
    public ResponseEntity<?> authenticate(@Valid @RequestBody UserDto dto)
    {
        return ResponseEntity.ok(new JwtResponse)
    }
*/
}