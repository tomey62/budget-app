package pl.zukowski.jwtauth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class TransactionGetDto {
    private Long price;
    private String category;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private String createdDate;
}
