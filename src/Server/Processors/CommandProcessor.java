package Server.Processors;

import Server.Commands.AbstractCommand;
import Server.Interfaces.FileHandler;

import java.util.HashMap;


/**
 * Handles console requests, executes commands
 */


public class CommandProcessor {
    private final HashMap<String, AbstractCommand> commands = new HashMap<>();
    private CollectionProcessor collectionProcessor;
    private final FileHandler fileHandler;

    public CommandProcessor(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void setCollectionProcessor(CollectionProcessor collectionProcessor) {
        this.collectionProcessor = collectionProcessor;
        this.collectionProcessor.setFileHandler(fileHandler);
        for (String key : commands.keySet()) {
            commands.get(key).setCollectionProcessor(collectionProcessor);
            commands.get(key).setFileHandler(fileHandler);
        }
    }

    public void addCommands(AbstractCommand... commands) {
        for (AbstractCommand command : commands) {
            this.commands.put(command.getConsoleName(), command);
            command.setCollectionProcessor(collectionProcessor);
            command.setFileHandler(fileHandler);
            command.setCommandProcessor(this);
        }
    }

    public HashMap<String, AbstractCommand> getCommands() {
        return commands;
    }
}