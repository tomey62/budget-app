package pl.zukowski.jwtauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.zukowski.jwtauth.dto.LocationDto;
import pl.zukowski.jwtauth.entity.Location;
import pl.zukowski.jwtauth.serviceImpl.LocationServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LocationController {
    private final LocationServiceImpl locationService;

    @GetMapping("/locations")
    public ResponseEntity<List<LocationDto>> getLocations(){
        return ResponseEntity.ok().body(locationService.getLocations());
    }
    @GetMapping("/location/{id}")
    public ResponseEntity<Optional<LocationDto>> getLocation(@PathVariable Long id){
        return ResponseEntity.ok().body(locationService.getLocation(id));
    }

    @GetMapping("/location/search")
    public List<LocationDto> searchLocations(@RequestParam(required = false) String country,
                                          @RequestParam(required = false) String city,
                                          @RequestParam(required = false) String name) {
        if (country == null && city == null && name == null) {
            // Obsłuż brak wpisanych parametrów - zwróć odpowiedni komunikat błędu lub pustą listę
            return Collections.emptyList();
        }

        if (country != null) {
            return locationService.searchByCountry(country);
        } else if (city != null) {
            return locationService.searchByCity(city);
        } else if (name != null) {
            return locationService.searchByName(name);
        }

        // Jeśli żadne z pól nie jest wypełnione, zwróć pustą listę
        return Collections.emptyList();

    }
}
