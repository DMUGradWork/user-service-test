package growmeet.userservice.exception;

public class IdentifierIsAlreadyExistException extends RuntimeException {
    public IdentifierIsAlreadyExistException(String message) {
        super(message);
    }
}
