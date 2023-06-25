package pl.zukowski.jwtauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationWithAverageRating {
    private Long id;
    private String name;
    private String description;
    private String country;
    private String city;
    private Double averageRating;
}
