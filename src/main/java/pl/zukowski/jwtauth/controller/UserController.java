package pl.zukowski.jwtauth.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zukowski.jwtauth.dto.UserDto;
import pl.zukowski.jwtauth.exception.ResourceConflictException;
import pl.zukowski.jwtauth.exception.ResourceNotFoundException;
import pl.zukowski.jwtauth.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() throws ResourceNotFoundException{
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/register")
    public void saveUser(@RequestBody UserDto user) throws ResourceConflictException {
        userService.saveUser(user);
    }

    @PatchMapping("/user/password/reset/{email}")
    public void resetPassword(@PathVariable String email) throws ResourceNotFoundException {
        userService.resetPassword(email);
    }

    @PatchMapping("/user/password/change")
    public void changePassword(HttpServletRequest request, @RequestParam String password) throws ResourceNotFoundException {
        userService.changePassword(request, password);
    }

}
