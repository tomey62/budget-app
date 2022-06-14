package pl.zukowski.jwtauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RoleToUser {
    private String login;
    private String role;
}
