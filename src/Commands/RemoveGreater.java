package Commands;

import Interfaces.StandartCommand;
import SpaceMarines.SpaceMarine;


/**
 * Removes elements greater than given.
 */


public class RemoveGreater extends AbstractCommand implements StandartCommand {
    public RemoveGreater(String nameInConsole) {
        super(nameInConsole, "(standart) Remove marines greater than given", "Marines removed!");
    }

    @Override
    public void execute() {
        SpaceMarine comparator = collectionProcessor.marineCreationFromInput();
        collectionProcessor.getCollection().removeIf(marine -> comparator.compareTo(marine) < 0);
    }
}
