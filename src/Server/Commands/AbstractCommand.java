package Server.Commands;

import Other.Requests.RequestDTO;
import Other.Responses.EmptyResponse;
import Other.Responses.AbstractResponse;
import Server.Interfaces.FileHandler;
import Server.Processors.CollectionProcessor;
import Server.Processors.CommandProcessor;

import java.io.IOException;

/**
 * Abstract Class for creating commands.
 */

public abstract class AbstractCommand {
    private final String consoleName;
    private final String consoleDescription;
    protected String regards;
    protected CollectionProcessor collectionProcessor;
    protected CommandProcessor commandProcessor;
    protected FileHandler fileHandler;

    public AbstractCommand(String consoleName, String consoleDescription) {
        this.consoleName = consoleName;
        this.consoleDescription = consoleDescription;
    }

    public AbstractCommand(String consoleName, String consoleDescription, String regards) {
        this.consoleName = consoleName;
        this.consoleDescription = consoleDescription;
        this.regards = regards;
    }

    public void setCollectionProcessor(CollectionProcessor collectionProcessor) {
        this.collectionProcessor = collectionProcessor;
    }

    public String getConsoleName() {
        return consoleName;
    }

    public String getConsoleDescription() {
        return consoleDescription;
    }

    public void setCommandProcessor(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void execute() {}

    public AbstractResponse execute(RequestDTO requestDTO) throws IOException {
        return new EmptyResponse();
    }
}