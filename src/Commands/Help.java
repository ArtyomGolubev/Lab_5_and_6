package Commands;

import Interfaces.StandartCommand;
import java.util.HashSet;

/**
 * Displays list of available commands.
 */


public class Help extends AbstractCommand implements StandartCommand {
    public Help(String nameInConsole) {
        super(nameInConsole, "(standart) Display list of available commands", "Command executed!");
    }

    @Override
    public void execute() {
        HashSet<AbstractCommand> availablecommands = collectionProcessor.getSender().getConsoleProcessor().getCommandProcessor().getCommands();
        StringBuilder out = new StringBuilder("Available commands:\n");
        for (AbstractCommand command : availablecommands) {
            out.append(String.format("%-" + 35 + "s | %s\n", command.getName(), command.getDescription()));
        }
        collectionProcessor.getSender().getConsoleProcessor().println(out);
    }
}