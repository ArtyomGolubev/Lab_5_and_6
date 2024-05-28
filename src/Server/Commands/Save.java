package Server.Commands;

import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.RegardsResponse;
import Server.Interfaces.StandartCommand;

/**
 * Saves collection in the given file. If path to the file is not stated - saves in the same file.
 */


public class Save extends AbstractCommand implements StandartCommand {
    public Save(String consoleName) {
        super(consoleName, "(pathtofile) Writes the Imperium Army list in the given book", "List written in the book!");
    }

    @Override
    public AbstractResponse execute(RequestDTO requestDTO) {
        fileHandler.write(collectionProcessor.getCollection(), collectionProcessor.getCollectionFileName());
        return new RegardsResponse(getConsoleName(), regards);
    }
}