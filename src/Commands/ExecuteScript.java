package Commands;

import Exceptions.*;
import Interfaces.ExtendedCommand;
import Processors.ConsoleProcessor;
import java.io.IOException;
import java.util.Stack;


/**
 * Read and execute the script from the specified file.
 * The script contains commands in the same form in which the user enters them interactively.
 */


public class ExecuteScript extends AbstractCommand implements ExtendedCommand {
    private final Stack<String>
            stackwithnames = new Stack<>();
    public ExecuteScript(String nameInConsole) {
        super(nameInConsole, "(pathtothebook) Aquire forbidden knowledge from the Black Library", "Forbidden knowledge aquired!");
    }

    @Override
    public void execute(String... params) throws WrongParameterException {
        try {
            if (!stackwithnames.contains(params[0])) {
                stackwithnames.push(params[0]);
                ConsoleProcessor.ScriptProcessor.readCommands(params[0], collectionProcessor.getSender().getConsoleProcessor().getCommandProcessor());
                stackwithnames.clear();
                sendRegards();
            }

        } catch (IOException | WrongParameterException | IncorrectFileNameException | NotFoundException | NonexistentCommandException | BlankRequestException ex) {
            throw new WrongParameterException("Either the book is missing from the Black Library or you're too miserable to access it");
        } catch (RecursionException ex) {
            collectionProcessor.getSender().getConsoleProcessor().printError(ex.toString());
        }
    }
}