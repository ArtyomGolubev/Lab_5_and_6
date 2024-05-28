package Server.Commands;

import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.RegardsResponse;
import Server.Interfaces.StandartCommand;

/**
 * Deletes every element from the collection.
 */

public class Clear extends AbstractCommand implements StandartCommand {
    public Clear(String consoleName) {
        super(consoleName, "(standart) clear the Imperium Army", "Army cleared! Good luck defending against the xenos");
    }

    @Override
    public AbstractResponse execute(RequestDTO requestDTO) {
        collectionProcessor.clearCollection();
        return new RegardsResponse(getConsoleName(), regards);
    }
}