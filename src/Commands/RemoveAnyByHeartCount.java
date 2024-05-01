package Commands;

import Exceptions.WrongParameterException;
import Interfaces.ExtendedCommand;
import Processors.Validator;
import SpaceMarines.SpaceMarine;


/**
 * Removes element with a certain amount of hearts.
 */


public class RemoveAnyByHeartCount extends AbstractCommand implements ExtendedCommand {
    public RemoveAnyByHeartCount(String nameInConsole) {
        super(nameInConsole, "(int heartCount) Remove marine with a given heart count from the Imperium Army", "Marine removed!");
    }

    @Override
    public void execute(String... parameters) throws WrongParameterException {
        if (Validator.CorrectNumberChecker(parameters[0], Integer.class)) {
            SpaceMarine marine = collectionProcessor.getMarinesByHeartCount(Integer.parseInt(parameters[0]))[0];
            collectionProcessor.getCollection().remove(marine);
            sendRegards();
        } else {
            throw new WrongParameterException("Wrong parameter entered");
        }
    }
}
