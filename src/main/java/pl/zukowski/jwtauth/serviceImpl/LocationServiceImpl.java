package pl.zukowski.jwtauth.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zukowski.jwtauth.entity.Location;
import pl.zukowski.jwtauth.repository.LocationRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LocationServiceImpl {
 private final LocationRepository locationRepository;

 public List<Location> getLocations (){
  return locationRepository.findAll();
 }

 public Optional<Location> getLocation (Long id){
  return locationRepository.findById(id);
 }

}
