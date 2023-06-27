package pl.zukowski.jwtauth.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zukowski.jwtauth.dto.LocationWithAverageRating;
import pl.zukowski.jwtauth.dto.UserDto;
import pl.zukowski.jwtauth.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
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
    @PostMapping("/favorite")
    public ResponseEntity<String> addLocationToFavorites(
            HttpServletRequest request,
            @RequestParam Long locationId) throws Exception {
        userService.addLocationToFavorites(request, locationId);
        return ResponseEntity.ok("Location added to favorites.");
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<LocationWithAverageRating>> getFavoriteLocationsWithAverageRating(
            HttpServletRequest request) throws Exception {
        List<LocationWithAverageRating> favoriteLocationsWithAvgRating = userService.getFavoriteLocations(request);
        return ResponseEntity.ok(favoriteLocationsWithAvgRating);
    }

    @DeleteMapping("/favorites")
    public ResponseEntity<String> removeLocationFromFavorites(HttpServletRequest request,
                                                              @RequestParam Long locationId) throws Exception {
        userService.removeLocationFromFavorites(request, locationId);
        return ResponseEntity.ok("Location removed from favorites");
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
