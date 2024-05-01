package Processors;

import Commands.AbstractCommand;
import Exceptions.*;
import Interfaces.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;


/**
 * Class for processing requests from console and executing commands.
 */


public class CommandProcessor {
    private final HashMap<String, AbstractCommand> commands = new HashMap<>();
    private CollectionProcessor processor;
    private final FileHandler fileHandler;

    public CommandProcessor(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void setCollectionProcessor(CollectionProcessor processor) {
        this.processor = processor;
        this.processor.setFileHandler(fileHandler);
        for (String key : commands.keySet()) {
            commands.get(key).setCollectionProcessor(processor);
            commands.get(key).setFileHandler(fileHandler);
        }
    }

    public void initiate(String input) throws IOException, WrongParameterException,  IncorrectFileNameException, NotFoundException, NonexistentCommandException, BlankRequestException {
        try {
            String[] splittedinput = splitRequest(input);
            String commandName = splittedinput[0];
            if (!commands.containsKey(commandName)) throw new NonexistentCommandException("The Adeptus Mechanicus sees this command as a heresy!");
            AbstractCommand command = this.commands.get(commandName);
            String[] params = new String[splittedinput.length-1];
            for (int i = 1; i < splittedinput.length; i++) {
                params[i-1] = splittedinput[i];
            }
            if (command instanceof ExtendedCommand && params.length != 0) {
                ((ExtendedCommand) command).execute(params);
            } else if (command instanceof StandartCommand) {
                ((StandartCommand) command).execute();
            } else if (params.length == 0 && command instanceof ExtendedCommand) {
                throw new WrongParameterException("Your command is incomplete, reverend Technopriest.");
            }
        } catch (WrongParameterException | NonexistentCommandException | BlankRequestException ex) {
            processor.getSender().getConsoleProcessor().printError(ex.toString());
            processor.getSender().getConsoleProcessor().getInput();
        }

    }

    private String[] splitRequest(String request) throws BlankRequestException {
        if (request.isEmpty()) throw new BlankRequestException("This request is blank as a world's surface after Exterminatus.");
        if (!request.contains(" ")) return new String[]{request};
        String command = request.split(" ", 2)[0];
        String[] params = request.split(" ", 2)[1].split(" ");
        if (params.length != 0) {
            for (int i = 0; i < params.length; i++) {
                if (params[i].isEmpty()) {
                    params[i] = null;
                }
            }
        }

        String[] processedRequest;
        if (Validator.EmptyArrayChecker(params)) {
            processedRequest = new String[]{command};
            return processedRequest;
        } else {
            processedRequest = new String[params.length + 1];
            processedRequest[0] = command;
            System.arraycopy(params, 0, processedRequest, 1, params.length);
        }
        return processedRequest;
    }

    public void processCommandsFromFile(String[] lines) throws IOException, IncorrectFileNameException, NotFoundException, WrongParameterException,  BlankRequestException, NonexistentCommandException {
        int i = 0;
        while (i < lines.length) {
            initiate(lines[i]);
            i++;
        }
    }

    public void addCommands(AbstractCommand... commands) {
        for (AbstractCommand command : commands) {
            this.commands.put(command.getName(), command);
            command.setCollectionProcessor(processor);
            command.setFileHandler(fileHandler);
        }
    }

    public HashSet<AbstractCommand> getCommands() {
        return new HashSet<>(commands.values());
    }
}