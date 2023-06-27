package pl.zukowski.jwtauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationDto {
    private Long id;
    private String name;
    private String description;
    private String country;
    private String city;
    private String category;
    private byte[] photo;
    private float averageRating;
}
