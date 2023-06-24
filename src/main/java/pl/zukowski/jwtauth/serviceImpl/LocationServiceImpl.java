package pl.zukowski.jwtauth.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zukowski.jwtauth.repository.LocationRepository;

@AllArgsConstructor
@Service
public class LocationServiceImpl {
 private final LocationRepository locationRepository;
}
