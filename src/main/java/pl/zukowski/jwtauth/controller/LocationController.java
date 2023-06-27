package pl.zukowski.jwtauth.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.zukowski.jwtauth.dto.LocationDto;
import pl.zukowski.jwtauth.entity.Location;
import pl.zukowski.jwtauth.serviceImpl.LocationServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class LocationController {
    private final LocationServiceImpl locationService;

    @GetMapping("/locations")
    public ResponseEntity<List<LocationDto>> getLocations(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok().body(locationService.getLocations(request));
    }
    @GetMapping("/location/{id}")
    public ResponseEntity<Optional<LocationDto>> getLocation(@PathVariable Long id, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok().body(locationService.getLocation(id, request));
    }

    @GetMapping("/location/search")
    public List<LocationDto> searchLocations(@RequestParam(required = false) String country,
                                          @RequestParam(required = false) String city,
                                          @RequestParam(required = false) String name, HttpServletRequest request) throws Exception {
        if (country == null && city == null && name == null) {
            // Obsłuż brak wpisanych parametrów - zwróć odpowiedni komunikat błędu lub pustą listę
            return Collections.emptyList();
        }

        if (country != null) {
            return locationService.searchByCountry(country,request);
        } else if (city != null) {
            return locationService.searchByCity(city,request);
        } else if (name != null) {
            return locationService.searchByName(name,request);
        }

        // Jeśli żadne z pól nie jest wypełnione, zwróć pustą listę
        return Collections.emptyList();

    }
}
