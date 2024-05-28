package Other.Requests;

import Other.Exceptions.WrongParameterException;

public class FilterContainsNameRequest extends AbstractRequest implements ExtendedRequest {
    private String substring;

    public FilterContainsNameRequest(String commandName) {
        super(commandName);
    }

    public String getSubstring() {
        return substring;
    }

    @Override
    public void setParameters(String... parameters) throws WrongParameterException {
        this.substring = parameters[0];
    }
}
