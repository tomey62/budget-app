package pl.zukowski.jwtauth.exception;

public class ResourceConflictException extends RuntimeException{

    public ResourceConflictException(String message)
    {
        super(message);
    }
}
