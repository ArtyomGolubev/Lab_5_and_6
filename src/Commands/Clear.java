package Commands;

import Interfaces.StandartCommand;


/**
 * Deletes every element from the collection.
 */

public class Clear extends AbstractCommand implements StandartCommand {
    public Clear(String consoleName) {
        super(consoleName, "(standart) clear the Imperium Army", "Army cleared! Good luck defending against the xenos");
    }

    @Override
    public void execute() {
        collectionProcessor.clearCollection();
        sendRegards();
    }
}