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
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LocationServiceImpl {
 private final LocationRepository locationRepository;
 private final ScoreRepository scoreRepository;
 private final ModelMapper modelMapper;

 public List<LocationDto> getLocations() {
  List<Location> locations = locationRepository.findAll();
  List<LocationDto> locationRatingDTOs = new ArrayList<>();
  byte[] photoData = null;
  for (Location location : locations) {
   if (location.getPhoto() != null){
    photoData =location.getPhoto();
   }
   float averageRating = calculateAverageRating(location.getId());
   LocationDto locationRatingDTO = new LocationDto(location.getId(),location.getName(), location.getDescription(), location.getCountry()
           , location.getCity(),location.getCategory(),photoData, averageRating);
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
  byte[] photoData = null;
  if (location.getPhoto() != null){
   photoData = location.getPhoto();
  }
  return Optional.of(new LocationDto(location.getId(),location.getName(), location.getDescription(), location.getCountry()
          , location.getCity(), location.getCategory(), photoData,averageRating));
 }



 public List<LocationDto> searchByCountry(String country) {
  List<Location> locations = locationRepository.searchByCountry(country);
  List<LocationDto> locationRatingDTOs = new ArrayList<>();
  byte[] photoData = null;
  for (Location location : locations) {
   if (location.getPhoto() != null){
    photoData = location.getPhoto();
   }
   float averageRating = calculateAverageRating(location.getId());
   LocationDto locationRatingDTO = new LocationDto(location.getId(),location.getName(), location.getDescription(), location.getCountry()
           , location.getCity(), location.getCategory(), photoData,averageRating);
   locationRatingDTOs.add(locationRatingDTO);
  }

  return locationRatingDTOs;
 }

 public List<LocationDto> searchByCity(String city) {
  List<Location> locations = locationRepository.searchByCity(city);
  List<LocationDto> locationRatingDTOs = new ArrayList<>();
  byte[] photoData = null;
  for (Location location : locations) {
   if (location.getPhoto() != null){
    photoData = location.getPhoto();
   }
   float averageRating = calculateAverageRating(location.getId());
   LocationDto locationRatingDTO = new LocationDto(location.getId(),location.getName(), location.getDescription(), location.getCountry()
           , location.getCity(), location.getCategory(),photoData,averageRating);
   locationRatingDTOs.add(locationRatingDTO);
  }

  return locationRatingDTOs;
 }
 public List<LocationDto> searchByName(String name) {
  List<Location> locations = locationRepository.searchByName(name);
  List<LocationDto> locationRatingDTOs = new ArrayList<>();
  byte[] photoData = null;
  for (Location location : locations) {
   if (location.getPhoto() != null){
    photoData = location.getPhoto();
   }
   float averageRating = calculateAverageRating(location.getId());
   LocationDto locationRatingDTO = new LocationDto(location.getId(),location.getName(), location.getDescription(), location.getCountry()
           , location.getCity(), location.getCategory(), photoData,averageRating);
   locationRatingDTOs.add(locationRatingDTO);
  }

  return locationRatingDTOs;
 }
}
