package pl.zukowski.jwtauth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RoleToUser {
    private String login;
    private String role;
}
