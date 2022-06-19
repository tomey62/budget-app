package pl.zukowski.jwtauth.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class TransactionDto {

    @NotNull
    private Long price;
    private String category;
    private String type;
}
