package pl.zukowski.jwtauth.serviceImpl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.zukowski.jwtauth.dto.LocationDto;
import pl.zukowski.jwtauth.entity.Location;
import pl.zukowski.jwtauth.entity.Score;
import pl.zukowski.jwtauth.repository.LocationRepository;
import pl.zukowski.jwtauth.repository.ScoreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LocationServiceImpl {
 private final LocationRepository locationRepository;
 private final ScoreRepository scoreRepository;
 private final ModelMapper modelMapper;

 public List<LocationDto> getLocations()
 {
  List<Location> locations = locationRepository.findAll();
  List<LocationDto> locationRatingDTOs = new ArrayList<>();

  for (Location location : locations) {
   float averageRating = calculateAverageRating(location.getId());
   LocationDto locationRatingDTO = new LocationDto(location.getName(),location.getDescription(),location.getCountry()
           ,location.getCity(), averageRating);
   locationRatingDTOs.add(locationRatingDTO);
  }

  return locationRatingDTOs;
 }

 private float calculateAverageRating(Long locationId) {
  List<Score> scores = scoreRepository.findByLocationId(locationId);
  if (scores.isEmpty()) {
   return 0.0f;
  }

  float sum = 0.0f;
  for (Score score : scores) {
   sum += score.getRating();
  }
  return sum / scores.size();
 }



 public Optional<LocationDto> getLocation(Long id) {
  Location location = locationRepository.getById(id);
  float averageRating = calculateAverageRating(id);
  return Optional.of(new LocationDto(location.getName(), location.getDescription(), location.getCountry()
          , location.getCity(), averageRating));
 }
}
