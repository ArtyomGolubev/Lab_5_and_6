package Commands;

import Interfaces.StandartCommand;


/**
 * Closes the program.
 */


public class Exit extends AbstractCommand implements StandartCommand {
    public Exit(String nameInConsole) {
        super(nameInConsole, "(standart) Close the Imperium Army management program", "Program closed");
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}