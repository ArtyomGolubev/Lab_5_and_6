package Server.Commands;

import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.InfoResponse;
import Server.Interfaces.StandartCommand;

/**
 * Displays information about the collection.
 */

public class Info extends AbstractCommand implements StandartCommand {
    public Info(String consoleName) {
        super(consoleName, "(standart) Display information about the Imperium Army", "Command executed!");
    }

    @Override
    public AbstractResponse execute(RequestDTO requestDTO) {
        return new InfoResponse(getConsoleName(), collectionProcessor.getData());
    }
}