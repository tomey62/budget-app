package pl.zukowski.jwtauth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SumDto {
    private Long sumTransaction;
    private String category;

    public SumDto(Long sumTransaction, String category) {
        this.sumTransaction = sumTransaction;
        this.category = category;
    }
}
