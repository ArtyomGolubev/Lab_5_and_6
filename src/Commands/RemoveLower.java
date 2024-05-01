package Commands;

import Interfaces.StandartCommand;
import SpaceMarines.SpaceMarine;


/**
 * Removes elements lower than given.
 */

public class RemoveLower extends AbstractCommand implements StandartCommand {
    public RemoveLower(String nameInConsole) {
        super(nameInConsole, "(standart) Remove marines lower than given", "Marines removed!");
    }

    @Override
    public void execute() {
        SpaceMarine comparator = collectionProcessor.marineCreationFromInput();
        collectionProcessor.getCollection().removeIf(marine -> comparator.compareTo(marine) > 0);
    }
}