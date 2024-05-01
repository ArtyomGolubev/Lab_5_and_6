package Commands;

import Interfaces.StandartCommand;
import SpaceMarines.SpaceMarine;


/**
 * Adds a new element to collection
 */


public class Add extends AbstractCommand implements StandartCommand {
    public Add(String nameInConsole) {
        super(nameInConsole, "(standart) Add a new marine to the Imperium Army", "New marine joined the Imperium Army ranks!");
    }

    @Override
    public void execute() {
        SpaceMarine recruit = collectionProcessor.marineCreationFromInput();
        collectionProcessor.addNewMarine(recruit);
        sendRegards();
    }
    
    public void executeFromScript(String... params) {
        collectionProcessor.marineCreationFromFile(params);
        sendRegards();
    }
}