package Processors;

import java.util.*;
import java.io.*;
import Exceptions.*;
import SpaceMarines.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;


/**
 * Class for processing user's inputs and sending them to command processor.
 */

public class ConsoleProcessor {
    private final Scanner scanner;
    private final CommandProcessor commandProcessor;

    public ConsoleProcessor(Scanner scanner, CommandProcessor commandProcessor) {
        this.scanner = scanner;
        this.commandProcessor = commandProcessor;
    }


    public String requestFilePath() {
        System.out.print("Enter the path to requested file: ");
        return scanner.nextLine();
    }

    public static class ScriptProcessor {
        public static void readCommands(String fileName, CommandProcessor commandProcessor) throws IOException, WrongParameterException, IncorrectFileNameException, NotFoundException, NonexistentCommandException, BlankRequestException {
            try {
                String[] commands = readScript(fileName);
                commandProcessor.processCommandsFromFile(commands);
            } catch (WrongParameterException ex) {
                throw new WrongParameterException(ex.toString());
            }
        }

        public static String[] readScript(String fileName) throws WrongParameterException {
            try {
                List<String> commands = new ArrayList<>();
                File file = new File(fileName);
                InputStreamReader in = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(in);
                String line;
                while ((line = reader.readLine()) != null) {
                    commands.add(line);
                    if (line.equals("execute_script src/script.txt")) {
                        throw new RecursionException("Recursive file access attempt.");
                    }
                    if (line.equals("execute_script src/script2.txt")) {
                        throw new RecursionException("Recursive file access attempt.");
                    }
                }
                return commands.toArray(new String[0]);
            } catch (IOException ex) {
                throw new WrongParameterException("Either file is not found or you don't have access to it.");
            }
        }
    }

    public void getInput() throws IncorrectFileNameException, NotFoundException, IOException, WrongParameterException, NonexistentCommandException, BlankRequestException {
        while (true) {
            try {
                print("> ");
                String request = scanner.nextLine();
                commandProcessor.initiate(request);
            } catch (NoSuchElementException ex) {
                System.exit(0);
                break;
            }
        }
    }

    public String ask(String message) {
        print(message);
        return scanner.nextLine();
    }

    public String askAstartesCategory(AstartesCategory[] categories) {
        println("Which specialization does this one have: ");
        for (AstartesCategory category : categories) {
            println(category.ordinal() + 1 + ") " + category.name());
        }
        return scanner.nextLine();
    }

    public String askWeaponType(Weapon[] weapons) {
        println("Which weapon does this one yield: ");
        for (Weapon weapon : weapons) {
            println(weapon.ordinal() + 1 + ") " + weapon.name());
        }
        return scanner.nextLine();
    }

    public String askWhatToChange() {
        println("Choose what you want to change. Declare such characteristics (via Space): ");
        Field[] fields = SpaceMarine.class.getDeclaredFields();
        List<Field> filteredFields = new ArrayList<>();

        for (Field field : fields) {
            if (!field.getName().equals("id") && !field.getName().equals("creationDate")) {
                filteredFields.add(field);
            }
        }
        Field[] finalArray = filteredFields.toArray(new Field[0]);
        for (int i = 1; i <= finalArray.length; i++) {
            println(i + ") " + finalArray[i-1].getName());
        }
        return scanner.nextLine();
    }

    public void print(Object obj) {
        System.out.print(obj.toString());
    }

    public void println(Object obj) {
        System.out.println(obj.toString());
    }

    public void printError(String message) {
        System.out.println("ERROR: " + message);
    }

    public void printAdvice(String advice) {
        System.out.println("ADVICE: " + advice);
    }

    public CommandProcessor getCommandProcessor() {
        return commandProcessor;
    }

}