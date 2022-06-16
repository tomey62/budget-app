package pl.zukowski.jwtauth.exception;

public class ResourceExistException extends RuntimeException{

    public ResourceExistException(String message)
    {
        super(message);
    }
}
