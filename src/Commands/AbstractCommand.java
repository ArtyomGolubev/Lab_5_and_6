package Commands;

import Interfaces.FileHandler;
import Processors.CollectionProcessor;


/**
 * Abstract Class for creating commands.
 */


public abstract class AbstractCommand {
    private final String name;
    private final String description;
    protected String regards;
    protected CollectionProcessor collectionProcessor;
    protected FileHandler fileHandler;

    public AbstractCommand(String name, String description, String regards) {
        this.name = name;
        this.description = description;
        this.regards = regards;
    }

    public void setCollectionProcessor(CollectionProcessor processor) {
        this.collectionProcessor = processor;
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    protected void sendRegards() {
        collectionProcessor.getSender().getConsoleProcessor().println(regards);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void execute(){};

}