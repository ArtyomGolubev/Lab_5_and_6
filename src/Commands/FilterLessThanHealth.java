package Commands;

import Exceptions.WrongParameterException;
import Interfaces.ExtendedCommand;
import Processors.Validator;
import SpaceMarines.SpaceMarine;


/**
 * Display elements whose 'health' field value is less than a given one.
 */


public class FilterLessThanHealth extends AbstractCommand implements ExtendedCommand {
    private float health;
    public FilterLessThanHealth(String nameInConsole) {
        super(nameInConsole, "(float health) Display every marine with amount of HP less than given", "Command executed!");
    }

    @Override
    public void execute(String... parameters) throws WrongParameterException {
        if (Validator.CorrectNumberChecker(parameters[0], Float.class)) {
            float health = Float.parseFloat(parameters[0]);
            SpaceMarine[] marines = collectionProcessor.getMarinesWithLessThanCertainHealth(health);
            for (SpaceMarine m : marines) {
                collectionProcessor.getSender().getConsoleProcessor().println(m);
            }
        } else {
            throw new WrongParameterException("Wrong parameter entered");
        }
    }
}