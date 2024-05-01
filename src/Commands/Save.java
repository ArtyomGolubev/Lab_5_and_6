package Commands;

import Exceptions.IncorrectFileNameException;
import Exceptions.NotFoundException;
import Interfaces.*;


/**
 * Saves collection in the given file. If path to the file is not stated - saves in the same file.
 */


public class Save extends AbstractCommand implements StandartCommand, ExtendedCommand  {
    public Save(String nameInConsole) {
        super(nameInConsole, "(pathtofile) Writes the Imperium Army list in the given book", "List written in the book!");
    }

    @Override // if path is stated
    public void execute(String... parameters) {
        if (parameters.length == 0) execute();
        else {
            try {
                if (parameters[0].matches("^\\D.*")) {
                    if (parameters[0].matches(".*\\.csv$")) {
                        String fileName = parameters[0];
                        fileHandler.write(collectionProcessor.getCollection(), fileName);
                        sendRegards();
                    } else throw new IncorrectFileNameException("The book should be available to Technopriests for reading (.csv only)");
                } else throw new IncorrectFileNameException("Techonpriests have never seen such a peculiar book title");
            } catch (IncorrectFileNameException ex) {
                collectionProcessor.getSender().getConsoleProcessor().printError(ex.toString());
            }
        }
    }
}