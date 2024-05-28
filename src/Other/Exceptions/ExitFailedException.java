package Other.Exceptions;

import java.io.IOException;

public class ExitFailedException extends Exception{
    public ExitFailedException(IOException message) {
        super(message);
    }
}
