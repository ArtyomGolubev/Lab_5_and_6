package Commands;

import Interfaces.ExtendedCommand;
import SpaceMarines.SpaceMarine;


/**
 *  Display marines whose 'name' field value contains a given substring.
 */


public class FilterContainsName extends AbstractCommand implements ExtendedCommand {
    public FilterContainsName(String nameInConsole) {
        super(nameInConsole, "(String name) Display marines whose 'name' field value contains a given substring", "Command executed!");
    }

    @Override
    public void execute(String... params) {
        SpaceMarine[] marines = collectionProcessor.getMarinesByName(params[0]);
        for (SpaceMarine m: marines) collectionProcessor.getSender().getConsoleProcessor().println(m);
    }
}