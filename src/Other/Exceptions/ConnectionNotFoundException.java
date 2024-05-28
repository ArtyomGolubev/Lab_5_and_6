package Other.Exceptions;

public class ConnectionNotFoundException extends RuntimeException{
    public ConnectionNotFoundException(String message) {
        super(message);
    }
}
