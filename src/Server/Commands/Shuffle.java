package Server.Commands;

import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.RegardsResponse;
import Server.Interfaces.StandartCommand;

/**
 * Shuffles elements in the collection.
 */


public class Shuffle extends AbstractCommand implements StandartCommand {
    public Shuffle(String consoleName) {
        super(consoleName, "(standart) Shuffles marines in the Imperium Army list", "Marines shuffled!");
    }

    @Override
    public AbstractResponse execute(RequestDTO requestDTO) {
        collectionProcessor.shuffleCollection();
        return new RegardsResponse(getConsoleName(), regards);
    }
}