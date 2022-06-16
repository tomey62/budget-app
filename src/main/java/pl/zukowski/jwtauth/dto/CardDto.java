package pl.zukowski.jwtauth.dto;

import lombok.Data;

@Data
public class CardDto {
    private Long cardNumber;
    private String name;
    private String type;
    private Long balance;
}
