package Server.Commands;

import Server.Interfaces.StandartCommand;

/**
 * Closes the program.
 */


public class Exit extends AbstractCommand implements StandartCommand {
    public Exit(String consoleName) {
        super(consoleName, "(standart) Close the Imperium Army management program", "Program closed");
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}