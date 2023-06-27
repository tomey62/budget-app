package pl.zukowski.jwtauth.serviceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zukowski.jwtauth.dto.LocationDto;
import pl.zukowski.jwtauth.dto.LocationWithAverageRating;
import pl.zukowski.jwtauth.dto.UserDto;
import pl.zukowski.jwtauth.entity.Location;
import pl.zukowski.jwtauth.entity.Role;
import pl.zukowski.jwtauth.entity.User;
import pl.zukowski.jwtauth.repository.LocationRepository;
import pl.zukowski.jwtauth.repository.RoleRepository;
import pl.zukowski.jwtauth.repository.ScoreRepository;
import pl.zukowski.jwtauth.repository.UserRepository;
import pl.zukowski.jwtauth.service.UserService;
import javax.persistence.EntityNotFoundException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;
    private final LocationRepository locationRepository;
    private final ScoreRepository scoreRepository;


    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, ModelMapper modelMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder, JavaMailSender javaMailSender
            , LocationRepository locationRepository, ScoreRepository scoreRepository) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
        this.locationRepository = locationRepository;
        this.scoreRepository = scoreRepository;
    }

    @Override
    public User saveUser(UserDto newUser) {

        User user = new User(null, newUser.getLogin(), bCryptPasswordEncoder.encode(newUser.getPassword()), newUser.getEmail(), new ArrayList<>(), new ArrayList<>());
        user.getRoles().add(roleRepo.findByName("ROLE_USER"));
        return userRepo.save(user);

    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String login, String roleName) {
        User user = userRepo.findByLogin(login);
        user.getRoles().add(roleRepo.findByName(roleName));
        userRepo.save(user);
    }


    @Override
    public User getUser(String login) {
        return userRepo.findByLogin(login);
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepo.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto convertEntityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User resetPassword(String email) {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String randomPassword = RandomStringUtils.random(length, useLetters, useNumbers);
        User user = userRepo.findByEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(randomPassword));
        try {
            sendMail(email, "Your new password", "Your new password is: " + randomPassword, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return userRepo.save(user);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userRepo.findByLogin(username);
                String access_token = JWT.create()
                        .withSubject(user.getLogin())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream()
                                .map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {
                response.sendError(CONFLICT.value());
                response.setHeader("error", exception.getMessage());
            }
        else {
            throw new RuntimeException("Refresh token is missing");
        }

    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach((role -> authorities.add(new SimpleGrantedAuthority(role.getName()))));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }

    public void sendMail(String to,
                         String subject,
                         String text,
                         boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public ResponseEntity<String> addLocationToFavorites(HttpServletRequest request, Long locationId) throws Exception {
        User user = getUserFromJwt(request);
        Location location = locationRepository.getById(locationId);
        if (!location.isFavourite()) {
            location.setFavourite(true);
            user.getLocations().add(location);
            userRepo.save(user);
            return ResponseEntity.ok("Location added to favorites");
        } else {
            return ResponseEntity.ok("Location is already in favorites");
        }
    }

    @Override
    public User getUserFromJwt(HttpServletRequest request) throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                return userRepo.findByLogin(username);
            } catch (Exception exception) {
                throw new RuntimeException("Token error");
            }
        else {
            throw new Exception("Token is missing");
        }

    }

    @Override
    public List<LocationDto> getFavoriteLocations(HttpServletRequest request) throws Exception {
        User user = getUserFromJwt(request);
        Collection<Location> favoriteLocations = user.getLocations();
        List<LocationDto> locationsWithAvgRating = new ArrayList<>();

        for (Location location : favoriteLocations) {
            byte[] photoData = null;

            if (location.getPhoto() != null) {
                photoData = location.getPhoto();
            }
            float avgRating = scoreRepository.getAverageRatingForLocation(location.getId());
            LocationDto locationWithAvgRating = new LocationDto(
            location.getId(),
                    location.getName(),
                    location.getDescription(),
                    location.getCountry(),
                    location.getCity(),
                    location.getCategory(),
                    photoData,
                    location.isFavourite(),
                    avgRating
                    );
                    locationsWithAvgRating.add(locationWithAvgRating);

        }
        return locationsWithAvgRating;
    }

    @Override
    public ResponseEntity<String> removeLocationFromFavorites(HttpServletRequest request, Long locationId) throws Exception {
        User user = getUserFromJwt(request);
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with id: " + locationId));

        // Sprawdź, czy użytkownik ma tę lokalizację w ulubionych
        if (location.isFavourite()) {
            location.setFavourite(false);
            user.getLocations().remove(location);
            userRepo.save(user);
            return ResponseEntity.ok("Location removed from favorites");
        } else {
            return ResponseEntity.ok("Location is not in favorites");
        }

    }
}
