package pl.zukowski.jwtauth.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class ExceptionDetails {
    private Date timestamp;
    private String message;
    private String details;
}
