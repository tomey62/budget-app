package pl.zukowski.jwtauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zukowski.jwtauth.serviceImpl.LocationServiceImpl;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LocationController {
    private final LocationServiceImpl locationService;
}
