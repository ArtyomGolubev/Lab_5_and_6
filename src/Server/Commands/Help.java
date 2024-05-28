package Server.Commands;

import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.HelpResponse;
import Server.Interfaces.StandartCommand;

import java.util.HashSet;

/**
 * Displays list of available commands.
 */


public class Help extends AbstractCommand implements StandartCommand {
    public Help(String consoleName) {
        super(consoleName, "(standart) Display list of available commands", "Command executed!");
    }

    @Override
    public AbstractResponse execute(RequestDTO requestDTO) {
        int padding = 30;
        HashSet<AbstractCommand> commands = new HashSet<>(commandProcessor.getCommands().values());
        StringBuilder out = new StringBuilder("List of available commands:\n");
        for (AbstractCommand c : commands) {
            out.append(String.format("%-" + padding + "s | %s\n", c.getConsoleName(), c.getConsoleDescription()));
        }
        return new HelpResponse(this.getConsoleName(), out.toString());
    }
}