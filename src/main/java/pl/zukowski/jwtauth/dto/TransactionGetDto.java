package pl.zukowski.jwtauth.dto;

import lombok.Data;

@Data
public class TransactionGetDto {
    private Long price;
    private String category;
    private String type;
    private String createdDate;
}
