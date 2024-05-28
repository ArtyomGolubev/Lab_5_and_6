package Other.Requests;

import Client.ClientHandlers.Checker;
import Other.Exceptions.WrongParameterException;

public class RemoveByIdRequest extends AbstractRequest implements ExtendedRequest {
    private Long id;
    public RemoveByIdRequest(String commandName) {
        super(commandName);
    }

    @Override
    public void setParameters(String... parameters) throws WrongParameterException {
        if (!parameters[0].isEmpty()) {
            if (Checker.CorrectNumberChecker(parameters[0], Long.class)) {
                this.id = Long.parseLong(parameters[0]);
            } else {
                throw new WrongParameterException("Wrong number entered.");
            }
        } else {
            throw new WrongParameterException("Parameter is empty.");
        }
    }

    public Long getId() {
        return id;
    }
}
