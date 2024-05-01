package Commands;

import Interfaces.StandartCommand;


/**
 * Shuffles elements in the collection.
 */


public class Shuffle extends AbstractCommand implements StandartCommand {
    public Shuffle(String nameInConsole) {
        super(nameInConsole, "(standart) Shuffles marines in the Imperium Army list", "Marines shuffled!");
    }

    @Override
    public void execute() {
        collectionProcessor.shuffleCollection();
        sendRegards();
    }
}