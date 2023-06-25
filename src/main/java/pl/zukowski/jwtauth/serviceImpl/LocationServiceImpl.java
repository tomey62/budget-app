package pl.zukowski.jwtauth.serviceImpl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.zukowski.jwtauth.dto.LocationDto;
import pl.zukowski.jwtauth.entity.Location;
import pl.zukowski.jwtauth.repository.LocationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LocationServiceImpl {
 private final LocationRepository locationRepository;
 private final ModelMapper modelMapper;

 public List<LocationDto> getLocations()
 {
  return locationRepository.findAll().stream()
          .map(this::convertEntityToDto).collect(Collectors.toList());
 }

 public Optional<LocationDto> getLocation(Long id) {
  return locationRepository.findById(id).map(this::convertEntityToDto);
 }
 public LocationDto convertEntityToDto(Location location) {
  return modelMapper.map(location, LocationDto.class);
 }
}
