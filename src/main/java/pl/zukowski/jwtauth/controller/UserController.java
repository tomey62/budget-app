package pl.zukowski.jwtauth.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zukowski.jwtauth.dto.UserDto;
import pl.zukowski.jwtauth.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> saveUser(@RequestBody UserDto user) {
        if (userService.getUser(user.getLogin()) == null)
            return ResponseEntity.ok().body(userService.saveUser(user));
        else
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User already exist");
    }

    @PatchMapping("/user/resetPassword/{email}")
    public ResponseEntity<?> resetPassword(@PathVariable String email) {
        return ResponseEntity.ok().body(userService.resetPassword(email));
    }
  /*
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role)
    {
        return ResponseEntity.ok().body(userService.saveRole(role));
    }

    @PostMapping("/role/add")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUser form)
    {
        userService.addRoleToUser(form.getLogin(), form.getRole());
        return ResponseEntity.ok().build();
    }
   */
}
