package Other.Requests;

import Other.Exceptions.WrongParameterException;

public interface ExtendedRequest {
    void setParameters(String... parameters) throws WrongParameterException;
}
