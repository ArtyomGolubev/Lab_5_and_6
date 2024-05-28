package Other.Requests;

import Client.ClientHandlers.Checker;
import Other.Exceptions.BlankRequestException;
import Other.Exceptions.WrongParameterException;

public class RemoveAnyByHeartCountRequest extends AbstractRequest implements ExtendedRequest {
    private Integer heartCount;
    public RemoveAnyByHeartCountRequest(String commandName) {
        super(commandName);
    }

    @Override
    public void setParameters(String... parameters) throws WrongParameterException {
        Integer result = -1;
        String parameter = parameters[0];
        try {
            if (Checker.isNullChecker(parameter) || Checker.EmptyArrayChecker(parameter.split(" "))) {
                throw new BlankRequestException("Empty string detected.");
            }
            if (parameter.contains(" ")) {
                String[] splitted = parameter.split(" ");
                if (Checker.CorrectNumberChecker(splitted[0], Integer.class)) {
                    result = Integer.parseInt(splitted[0]);
                }
            }
            else if (Checker.CorrectNumberChecker(parameter, Integer.class)) {
                result = Integer.parseInt(parameter);
            }
            else {
                throw new WrongParameterException("Wrong number format.");
            }
            if (result > 0 && result < 4) {
                this.heartCount = result;
            }
            else {
                throw new WrongParameterException("Something's wrong with the number.");
            }
        } catch (WrongParameterException | BlankRequestException ex) {
            throw new WrongParameterException("Wrong parameter.");
        }
    }

    public Integer getHeartCount() {
        return heartCount;
    }
}
