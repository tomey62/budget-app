package pl.zukowski.jwtauth.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zukowski.jwtauth.entity.Location;
import pl.zukowski.jwtauth.repository.LocationRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class LocationServiceImpl {
 private final LocationRepository locationRepository;

 public List<Location> getLocations (){
  return locationRepository.findAll();
 }
}
