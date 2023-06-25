package pl.zukowski.jwtauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.zukowski.jwtauth.entity.Score;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationDto {
    private String name;
    private String description;
    private String country;
    private String city;

}
