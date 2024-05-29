package Client.ClientHandlers;

import Other.Exceptions.*;
import Other.SpaceMarines.*;
import Other.Requests.*;
import Other.Responses.*;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * Class that processes inputs and sends them to CommandProcessor
 */


public class ConsoleHandler {
    private final Scanner scanner = new Scanner(System.in);
    private final RequestHandler requestHandler;
    private final Sender sender;
    private ConsoleMode consoleMode;
    private final ResponseHandler responseHandler;
    private final Asker asker = new Asker();
    private final ScriptProcessor scriptProcessor = new ScriptProcessor();


    public ConsoleHandler(RequestHandler requestHandler, Sender sender, ResponseHandler responseHandler) {
        this.requestHandler = requestHandler;
        this.sender = sender;
        this.responseHandler = responseHandler;
        this.consoleMode = ConsoleMode.INTERACTIVE;
    }

    public class Asker {
        public void MarineCreationFromInput(AddRequest request) {
            request.setName(askName());
            request.setCoordinates(askCoordinates());
            request.setHealth(askHealth());
            request.setHeartCount(askHeartCount());
            request.setCategory(askCategory());
            request.setWeapon(askWeapon());
            request.setChapter(askChapter());
        }

        public void updateMarine(UpdateRequest request) {
            String answ = askForChanges();
            try {
                if (Checker.StringForIntegerChecker(answ)) {
                    String[] splitted = answ.split(" ");
                    int[] fieldNumbers = new int[splitted.length];
                    for (int i = 0; i < fieldNumbers.length; i++) {
                        fieldNumbers[i] = Integer.parseInt(splitted[i]);
                    }
                    for (int num : fieldNumbers) {
                        if (num > 7) {
                            throw new WrongParameterException("Number " + num + " does not correlate with any fields.");
                        }
                        switch (num) {
                            case 1 -> request.setName(askName());
                            case 2 -> request.setCoordinates(askCoordinates());
                            case 3 -> request.setHealth(askHealth());
                            case 4 -> request.setHeartCount(askHeartCount());
                            case 5 -> request.setCategory(askCategory());
                            case 6 -> request.setWeapon(askWeapon());
                            case 7 -> request.setChapter(askChapter());
                        }
                    }
                } else throw new WrongParameterException("Only numbers and spaces are allowed.");
            } catch (WrongParameterException ex) {
                printError(ex.toString());
            }
        }

        public String askName() {
            String name = ask("Enter Marine's name: ");
            try {
                if (Checker.ValidNameChecker(name)) {
                    return name;
                } else {
                    throw new WrongParameterException("Name can't be empty.");
                }
            } catch (WrongParameterException ex) {
                printError(ex.toString());
                return askName();
            }
        }

        public Coordinates askCoordinates() {
            String responseX = ask("Enter marine's x coordinate using float number): ");
            String responseY = ask("Enter marine's y coordinate using float number): ");
            Float x;
            float y;
            try {
                if (Checker.CorrectNumberChecker(responseX.split(" ")[0], Float.class) && Checker.CorrectNumberChecker(responseY.split(" ")[0], Float.class)) {
                    if (!Checker.isNullChecker(responseX.split(" ")[0]) && !Checker.isNullChecker(responseY.split(" ")[0])) {
                        x = Float.parseFloat(responseX.split(" ")[0]);
                        y = Float.parseFloat(responseY.split(" ")[0]);
                        if (x <= 138.0f) {
                            return new Coordinates(x, y);
                        }
                        else throw new WrongParameterException("The x coordinate is too big");
                    } else {
                        throw new WrongParameterException("Coordinate can't be null.");
                    }
                } else {
                    throw new WrongParameterException("Something's wrong with the number format.");
                }
            } catch (WrongParameterException ex) {
                printError(ex.toString());
                return askCoordinates();
            }
        }

        public float askHealth() {
            float result = -1.0f;
            String input = ask("Enter marine's HP count: ");

            try {
                if (Checker.isNullChecker(input) || Checker.EmptyArrayChecker(input.split(" "))) {
                    throw new BlankRequestException("This field can't be blank.");
                }
                if (input.contains(" ")) {
                    String[] splitted = input.split(" ");
                    if (Checker.CorrectNumberChecker(splitted[0], Float.class)) {
                        result = Float.parseFloat(splitted[0]);
                    }
                } else if (Checker.CorrectNumberChecker(input, Float.class)) {
                    result = Float.parseFloat(input);
                } else {
                    throw new WrongParameterException("Something's wrong with the number format.");
                }
                if (result > 0.0f) {
                    return result;
                } else {
                    throw new WrongParameterException("This field can't be less than 0.");
                }
            } catch (WrongParameterException | BlankRequestException ex) {
                printError(ex.toString());
                return askHealth();
            }
        }

        public int askHeartCount()  {
            int result = -1;
            String input = ask("How many hearts does marine have: ");

            try {
                if (Checker.isNullChecker(input) || Checker.EmptyArrayChecker(input.split(" "))) {
                    throw new BlankRequestException("Blank string entered.");
                }
                if (input.contains(" ")) {
                    String[] splitted = input.split(" ");
                    if (splitted.length > 1) {
                        throw new WrongParameterException("Something's wrong with the input format.");
                    }
                    if (Checker.CorrectNumberChecker(splitted[0], Integer.class)) {
                        if (Integer.valueOf(input) < 4) {
                            result = Integer.parseInt(splitted[0]);
                        }
                    }
                } else if (Checker.CorrectNumberChecker(input, Integer.class)) {
                    if (Integer.valueOf(input) < 4 && input.length()<2) {
                        result = Integer.parseInt(input);
                    }
                } else {
                    throw new WrongParameterException("This field can only take values from 1 to 3.");
                }
                if (result > 0) {
                    return result;
                } else {
                    throw new WrongParameterException("This field can't be less than 1.");
                }
            } catch (WrongParameterException | BlankRequestException ex) {
                printError(ex.toString());
                return askHeartCount();
            }
        }

        public AstartesCategory askCategory() {
            AstartesCategory[] categories = AstartesCategory.values();
            StringBuilder question = new StringBuilder();
            question.append("Enter Astartes Category number: \n");
            for (AstartesCategory c : categories) {
                question.append(c.ordinal() + 1).append(". ").append(c.name()).append("\n");
            }
            String input = ask(question.toString());
            String num;
            try {
                if (Checker.isNullChecker(input) || Checker.EmptyArrayChecker(input.split(" "))) {
                    throw new BlankRequestException("Field can't be blank.");
                }
                if (input.contains(" ")) {
                    num = input.split(" ")[0];
                    if (Checker.isNullChecker(num)) {
                        throw new WrongParameterException("Something's wrong with the input format.");
                    }
                } else {
                    num = input;
                }
                if (Checker.CorrectNumberChecker(num, Integer.class)) {
                    if (Integer.parseInt(num) <= AstartesCategory.values().length && Integer.parseInt(num) >= 1) {
                        return AstartesCategory.values()[Integer.parseInt(num)-1];
                    } else {
                        throw new WrongParameterException("Wrong number entered.");
                    }
                } else {
                    throw new WrongParameterException("Something's wrong with the number format.");
                }

            } catch (WrongParameterException | NumberFormatException | BlankRequestException ex) {
                printError(ex.toString());
                return askCategory();
            }
        }

        public Weapon askWeapon() {
            Weapon[] weapons = Weapon.values();
            StringBuilder question = new StringBuilder();
            question.append("Enter Marine's weapon number: \n");
            for (Weapon w : weapons) {
                question.append(w.ordinal() + 1).append(". ").append(w.name()).append("\n");
            }
            String response = ask(question.toString());
            String num;
            try {
                if (Checker.isNullChecker(response) || Checker.EmptyArrayChecker(response.split(" "))) {
                    throw new BlankRequestException("Field can't be blank.");
                }
                if (response.contains(" ")) {
                    num = response.split(" ")[0];
                    if (Checker.isNullChecker(num)) {
                        throw new WrongParameterException("Something's wrong with the input format.");
                    }

                } else {
                    num = response;
                }
                if (Checker.CorrectNumberChecker(num, Integer.class)) {
                    if (Integer.parseInt(num) <= Weapon.values().length && Integer.parseInt(num) >= 1) {
                        return Weapon.values()[Integer.parseInt(num)-1];
                    } else {
                        throw new WrongParameterException("Wrong number.");
                    }
                } else {
                    throw new WrongParameterException("Something's wrong with the number format.");
                }

            } catch (WrongParameterException | NumberFormatException | BlankRequestException ex) {
                printError(ex.toString());
                return askWeapon();
            }
        }

        public Chapter askChapter() {
            String chapter = ask("Enter marine's chapter: ");
            String parentlegion = ask("Enter marine's parent legion: ");
            try {
                if (Checker.ValidNameChecker(chapter) && Checker.ValidNameChecker(parentlegion)) {
                    return new Chapter(chapter, parentlegion);
                } throw new WrongParameterException("Something's wrong with the input format.");
            } catch (WrongParameterException ex) {
                printError(ex.toString());
                return askChapter();
            }
        }
    }


    public class ScriptProcessor {
        private Stack<String> fileNames = new Stack<>();
        private Queue<String> commands = new ArrayDeque<>();

        public String nextLine() {
            try {
                return this.commands.poll();
            } finally {
                if (commands.isEmpty()) {
                    consoleMode = ConsoleMode.INTERACTIVE;
                    commands = new ArrayDeque<>();
                }
            }
        }

        public void clear() {
            this.commands.clear();
        }

        private void readScript(String fileName) throws WrongParameterException {
            try {
                fileNames.push(fileName);
                File file = new File(fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.equals("execute_script " + fileName)) {
                        throw new RecursionException("Recursive file access attempt.");
                    }
                    this.commands.add(line);
                }
            } catch (IOException ex) {
                throw new WrongParameterException("File is not found / You don't have access to the requested file.");
            }
        }
    }

    public void takeInput() {
        while (true) {
            try {
                print("> ");
                String request = next();
                AbstractResponse response = processRequest(request);
                println(this.responseHandler.handleResponse(response));
            } catch (Exception ex) {
                printError(ex.toString());
                break;
            }
        }
    }

    public String next() {
        if (this.consoleMode == ConsoleMode.INTERACTIVE) {
            return scanner.nextLine();
        } else if (this.consoleMode == ConsoleMode.FILE_READER) {
            return scriptProcessor.nextLine();
        }
        return scanner.nextLine();
    }

    public AbstractResponse processRequest(String request) {
        try {
            String[] processed = splitUserRequest(request);
            AbstractRequest requestToServer = this.requestHandler.get(processed[0]);
            if (requestToServer instanceof ExtendedRequest) {
                String[] parameters = new String[processed.length-1];
                for (int i = 1; i < processed.length; i++) {
                    parameters[i-1] = processed[i];
                }
                ((ExtendedRequest) requestToServer).setParameters(parameters);
            }
            if (requestToServer instanceof AddRequest) {
                this.asker.MarineCreationFromInput((AddRequest) requestToServer);
            }
            if (requestToServer instanceof UpdateRequest) {
                this.asker.updateMarine((UpdateRequest) requestToServer);
            }
            if (requestToServer instanceof ExecuteScriptRequest) {
                this.consoleMode = ConsoleMode.FILE_READER;
                scriptProcessor.readScript(((ExecuteScriptRequest) requestToServer).getFileName());
                return new RegardsResponse(requestToServer.getCommandName(), "Script reading initiated.");
            }
            return this.sender.sendRequest(requestToServer);
        } catch (BlankRequestException | NonexistentCommandException | WrongParameterException ex) {
            return new ErrorResponse(ex.toString());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String[] splitUserRequest(String request) throws BlankRequestException {
        if (request.isEmpty()) throw new BlankRequestException("Blank string entered.");
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

        String[] processed;
        if (Checker.NullArrayChecker(params)) {
            processed = new String[]{command};
            return processed;
        } else {
            processed = new String[params.length + 1];
            processed[0] = command;
            System.arraycopy(params, 0, processed, 1, params.length);
        }
        return processed;
    }

    public String ask(String message) {
        print(message);
        return next();
    }

    public String askForChanges() {
        println("Pick parameters you'd like to change and write them (via Space): ");
        Field[] fields = SpaceMarine.class.getDeclaredFields();
        List<Field> filteredFields = new ArrayList<>();

        for (Field field : fields) {
            if (!field.getName().equals("id") && !field.getName().equals("creationDate")) {
                filteredFields.add(field);
            }
        }
        Field[] resultingArray = filteredFields.toArray(new Field[0]);
        for (int i = 1; i <= resultingArray.length; i++) {
            println(i + ") " + resultingArray[i-1].getName());
        }
        return next();
    }

    public void println(Object obj) {
        if (consoleMode == ConsoleMode.INTERACTIVE || obj != null) {
            System.out.println(obj.toString());
        }
    }

    public void print(Object obj) {
        if (consoleMode == ConsoleMode.INTERACTIVE) {
            System.out.print(obj.toString());
        }
    }

    public void printAdvice(String advice) {
        if (consoleMode == ConsoleMode.INTERACTIVE) {
            System.out.println("ADVICE: " + advice);
        }
    }

    public void printError(String message) {
        System.out.println("ERROR: " + message);
        if (this.consoleMode == ConsoleMode.FILE_READER) {
            this.scriptProcessor.clear();
            System.out.println("File execution finished.");
            this.consoleMode = ConsoleMode.INTERACTIVE;
            takeInput();
        }
    }
}