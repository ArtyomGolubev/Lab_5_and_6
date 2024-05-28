package Other.Requests;

import Client.ClientHandlers.Checker;
import Other.Exceptions.BlankRequestException;
import Other.Exceptions.WrongParameterException;

public class FilterLessThanHealthRequest extends AbstractRequest implements ExtendedRequest {
    private float health;
    public FilterLessThanHealthRequest(String commandName) {
        super(commandName);
    }

    public void setParameters(String... parameters) throws WrongParameterException {
        float result = -1.0f;
        String parameter = parameters[0];

        try {
            if (Checker.isNullChecker(parameter) || Checker.EmptyArrayChecker(parameter.split(" "))) {
                throw new BlankRequestException("You didn't enter the HP number.");
            }
            if (parameter.contains(" ")) {
                String[] splitted = parameter.split(" ");
                if (Checker.CorrectNumberChecker(splitted[0], Float.class)) {
                    result = Float.parseFloat(splitted[0]);
                }
            }
            else if (Checker.CorrectNumberChecker(parameter, Float.class)) {
                result = Float.parseFloat(parameter);
            }
            else {
                throw new WrongParameterException("Something's wrong with the number format.");
            }
            if (result > 0.0f) {
                this.health = result;
            } else {
                throw new WrongParameterException("HP field is always bigger than 0.");
            }
        } catch (WrongParameterException | BlankRequestException ex) {
            throw new WrongParameterException("Wrong parameter entered.");
        }
    }

    public float getHealth() {
        return health;
    }
}
