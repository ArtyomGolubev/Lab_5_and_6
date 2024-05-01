package Commands;

import Interfaces.StandartCommand;


/**
 * Displays information about the collection.
 */

public class Info extends AbstractCommand implements StandartCommand {
    public Info(String nameInConsole) {
        super(nameInConsole, "(standart) Display information about the Imperium Army", "Command executed!");
    }

    @Override
    public void execute() {
        collectionProcessor.getSender().getConsoleProcessor().println(collectionProcessor.getData());
    }
}