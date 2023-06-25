package pl.zukowski.jwtauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zukowski.jwtauth.entity.Location;
import pl.zukowski.jwtauth.serviceImpl.LocationServiceImpl;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LocationController {
    private final LocationServiceImpl locationService;

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getLocations(){
        return ResponseEntity.ok().body(locationService.getLocations());
    }
}
