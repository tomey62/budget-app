package pl.zukowski.jwtauth.serviceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.zukowski.jwtauth.dto.LocationDto;
import pl.zukowski.jwtauth.entity.Location;
import pl.zukowski.jwtauth.entity.Score;
import pl.zukowski.jwtauth.entity.User;
import pl.zukowski.jwtauth.repository.LocationRepository;
import pl.zukowski.jwtauth.repository.ScoreRepository;
import pl.zukowski.jwtauth.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@AllArgsConstructor
@Service
public class LocationServiceImpl {
 private final LocationRepository locationRepository;
 private final ScoreRepository scoreRepository;
 private final UserRepository userRepository;
 private final ModelMapper modelMapper;

 public List<LocationDto> getLocations(HttpServletRequest request) throws Exception {
  List<Location> locations = locationRepository.findAll();
  User user = getUserFromJwt(request);
  List<LocationDto> locationDtos = new ArrayList<>();

  for (Location location : locations) {
   LocationDto locationDto = new LocationDto();
   locationDto.setId(location.getId());
   locationDto.setName(location.getName());
   locationDto.setDescription(location.getDescription());
   locationDto.setCountry(location.getCountry());
   locationDto.setCity(location.getCity());
   locationDto.setCategory(location.getCategory());

   // Sprawdzenie, czy lokalizacja jest ulubiona
   if (user != null && user.getLocations().contains(location)) {
    locationDto.setFavourite(true);
   } else {
    locationDto.setFavourite(false);
   }
   locationDtos.add(locationDto);
  }

  return locationDtos;
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


 public Optional<LocationDto> getLocation(Long id, HttpServletRequest request) throws Exception {
  Location location = locationRepository.getById(id);
  float averageRating = calculateAverageRating(id);
  byte[] photoData = null;
  if (location.getPhoto() != null) {
   photoData = location.getPhoto();
  }

  boolean isFavorite = false;
   User user = getUserFromJwt(request);
   if (user != null && user.getLocations().contains(location)) {
    isFavorite = true;
   }


  LocationDto locationDto = new LocationDto(location.getId(), location.getName(), location.getDescription(), location.getCountry(),
          location.getCity(), location.getCategory(), photoData, isFavorite, averageRating);

  return Optional.of(locationDto);
 }


 public List<LocationDto> searchByCountry(String country, HttpServletRequest request) throws Exception {
  List<Location> locations = locationRepository.searchByCountry(country);
  List<LocationDto> locationRatingDTOs = new ArrayList<>();

  for (Location location : locations) {
   byte[] photoData = null;

   if (location.getPhoto() != null) {
    photoData = location.getPhoto();
   }

   float averageRating = calculateAverageRating(location.getId());

   boolean isFavorite = false;

    User user = getUserFromJwt(request);
    if (user != null && user.getLocations().contains(location)) {
     isFavorite = true;
    }


   LocationDto locationRatingDTO = new LocationDto(
           location.getId(),
           location.getName(),
           location.getDescription(),
           location.getCountry(),
           location.getCity(),
           location.getCategory(),
           photoData,
           isFavorite,
           averageRating
   );

   locationRatingDTOs.add(locationRatingDTO);
  }

  return locationRatingDTOs;
 }

 public List<LocationDto> searchByCity(String city, HttpServletRequest request) throws Exception {
  List<Location> locations = locationRepository.searchByCity(city);
  List<LocationDto> locationRatingDTOs = new ArrayList<>();

  for (Location location : locations) {
   byte[] photoData = null;

   if (location.getPhoto() != null) {
    photoData = location.getPhoto();
   }

   float averageRating = calculateAverageRating(location.getId());

   boolean isFavorite = false;

    User user = getUserFromJwt(request);
    if (user != null && user.getLocations().contains(location)) {
     isFavorite = true;
    }


   LocationDto locationRatingDTO = new LocationDto(
           location.getId(),
           location.getName(),
           location.getDescription(),
           location.getCountry(),
           location.getCity(),
           location.getCategory(),
           photoData,
           isFavorite,
           averageRating
   );

   locationRatingDTOs.add(locationRatingDTO);
  }

  return locationRatingDTOs;
 }

 public List<LocationDto> searchByName(String name, HttpServletRequest request) throws Exception {
  List<Location> locations = locationRepository.searchByName(name);
  List<LocationDto> locationRatingDTOs = new ArrayList<>();

  for (Location location : locations) {
   byte[] photoData = null;

   if (location.getPhoto() != null) {
    photoData = location.getPhoto();
   }

   float averageRating = calculateAverageRating(location.getId());

   boolean isFavorite = false;

    User user = getUserFromJwt(request);
    if (user != null && user.getLocations().contains(location)) {
     isFavorite = true;
    }


   LocationDto locationRatingDTO = new LocationDto(
           location.getId(),
           location.getName(),
           location.getDescription(),
           location.getCountry(),
           location.getCity(),
           location.getCategory(),
           photoData,
           isFavorite,
           averageRating
   );

   locationRatingDTOs.add(locationRatingDTO);
  }

  return locationRatingDTOs;
 }

 public User getUserFromJwt(HttpServletRequest request) throws Exception {
  String authorizationHeader = request.getHeader(AUTHORIZATION);
  if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
   try {
    String refresh_token = authorizationHeader.substring("Bearer ".length());
    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
    DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
    String username = decodedJWT.getSubject();
    return userRepository.findByLogin(username);
   } catch (Exception exception) {
    throw new RuntimeException("Token error");
   }
  else {
   throw new Exception("Token is missing");
  }
 }
}
