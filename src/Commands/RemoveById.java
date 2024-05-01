package Commands;

import Exceptions.NotFoundException;
import Exceptions.WrongParameterException;
import Interfaces.ExtendedCommand;


/**
 * Removes element from the collection by id.
 */


public class RemoveById extends AbstractCommand implements ExtendedCommand {
    public RemoveById(String nameInConsole) {
        super(nameInConsole, "(long id) Remove marine from the Imperium Army by id", "Marine removed!");
    }

    @Override
    public void execute(String... parameters) throws WrongParameterException {
        if (parameters[0].isEmpty()) throw new WrongParameterException("Parameter is empty");
        try {
            collectionProcessor.removeMarineById((Long.parseLong(parameters[0])));
            sendRegards();
        } catch (NumberFormatException ex) {
            throw new WrongParameterException("Wrong format of parameter. Try again");
        } catch (NotFoundException ex) {
            collectionProcessor.getSender().getConsoleProcessor().printError(ex.toString());
        }
    }
}