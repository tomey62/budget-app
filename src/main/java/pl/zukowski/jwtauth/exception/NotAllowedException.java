package pl.zukowski.jwtauth.exception;

public class NotAllowedException extends RuntimeException{
    public NotAllowedException(String message)
    {
        super(message);
    }
}
