package Commands;

import Exceptions.*;
import Interfaces.*;
import Processors.Validator;
import SpaceMarines.SpaceMarine;


/**
 * Shows the collection.
 */


public class Show extends AbstractCommand implements StandartCommand, ExtendedCommand {

    public Show(String nameInConsole) {
        super(nameInConsole, "(int amount) Shows the Imperium Army list", "Imperium Army list shown!");
    }

    @Override
    public void execute() {
        if (!collectionProcessor.getCollection().isEmpty()) {
            for (SpaceMarine marine : collectionProcessor.getCollection()) {
                collectionProcessor.getSender().getConsoleProcessor().println(marine);
            }
        } else {
            collectionProcessor.getSender().getConsoleProcessor().println("The Imperium Army is empty. God-Emperor disapproves(");
        }

    }

    @Override
    public void execute(String... parameters) throws WrongParameterException {
        if (parameters.length == 0) {
            execute();
        } else {
            String parameter = parameters[0];
            if (parameter == null) {
                execute();
            } else if (Validator.CorrectNumberChecker(parameter, Integer.class)) {
                int line_up = Integer.parseInt(parameter);
                if (line_up > 0 && line_up <= collectionProcessor.getCollection().size()) {
                    for (int i = 0; i < line_up; i++) {
                        collectionProcessor.getSender().getConsoleProcessor().println(collectionProcessor.getCollection().get(i));
                    }
                } else if (line_up < 0) {
                    throw new WrongParameterException("Can't show negative amount of units");
                } else if (line_up > collectionProcessor.getCollection().size()) {
                    throw new WrongParameterException("There are many marines in the Army.. But not that (" + collectionProcessor.getCollection().size() + ") many.");
                }
            } else {
                throw new WrongParameterException("Wrong parameter entered");
            }
        }
    }
}
