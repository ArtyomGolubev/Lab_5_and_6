package Processors;

import Exceptions.BlankRequestException;
import Exceptions.WrongParameterException;
import SpaceMarines.*;

public class Sender {
    private final ConsoleProcessor consoleProcessor;

    public Sender(ConsoleProcessor consoleProcessor) {
        this.consoleProcessor = consoleProcessor;
    }

    public String nameRequest() {
        String input = consoleProcessor.ask("Enter marine's name: ");
        try {
            if (Validator.ValidNameChecker(input)) {
                return input;
            } else {
                throw new WrongParameterException("Every marine has a name, dammit!");
            }
        } catch (WrongParameterException ex) {
            consoleProcessor.printError(ex.toString());
            return nameRequest();
        }
    }

    public Coordinates coordinatesRequest() {
        String response = consoleProcessor.ask("Enter marine's coordinates (via Space, using integers): ");
        float x;
        float y;
        try {
            if (response.split(" ").length < 2) {
                throw new WrongParameterException("Don't be an idiot and enter either x or y coordinates.");
            }
            if (Validator.CorrectNumberChecker(response.split(" ")[0], Float.class) && Validator.CorrectNumberChecker(response.split(" ")[1], Float.class)) {
                if (!Validator.isNullChecker(response.split(" ")[0])) {
                    x = Float.parseFloat(response.split(" ")[0]);
                    y = Float.parseFloat(response.split(" ")[1]);
                    if (x < 139) {
                        return new Coordinates(x, y);
                    }
                    else throw new WrongParameterException("The x coordinate is too big");
                } else {
                    throw new WrongParameterException("Marine can't be 1D.");
                }
            } else {
                throw new WrongParameterException("Something's wrong with the number format.");
            }
        } catch (WrongParameterException ex) {
            consoleProcessor.printError(ex.toString());
            return coordinatesRequest();
        }
    }

    public float healthRequest() {
        float result = -1.0f;
        String input = consoleProcessor.ask("Enter marine's HP count: ");

        try {
            if (Validator.isNullChecker(input) || Validator.EmptyArrayChecker(input.split(" "))) {
                throw new BlankRequestException("HP field can't be blank.");
            } else if (input.contains(" ")) {
                String[] splitted = input.split(" ");
                if (Validator.CorrectNumberChecker(splitted[0], Float.class)) {
                    result = Float.parseFloat(splitted[0]);
                }
            } else if (Validator.CorrectNumberChecker(input, Float.class)) {
                result = Float.parseFloat(input);
            } else {
                throw new WrongParameterException("Something's wrong with the number format.");
            }
            if (result > 0.0f) {
                return result;
            } else {
                throw new WrongParameterException("Marines never die. They only blink for an indefinite amount of time.");
            }
        } catch (WrongParameterException | BlankRequestException ex) {
            consoleProcessor.printError(ex.toString());
            return healthRequest();
        }
    }

    public int heartCountRequest()  {
        int result = -1;
        String input = consoleProcessor.ask("How much hearts does marine have: ");

        try {
            if (Validator.isNullChecker(input) || Validator.EmptyArrayChecker(input.split(" "))) {
                throw new WrongParameterException("That's not an amount of hearts marine can have!");
            }
            if (input.contains(" ")) {
                String[] splitted = input.split(" ");
                if (splitted.length>1) {
                    throw new BlankRequestException("Don't be an idiot and enter amount of hearts");
                }
                if (Validator.CorrectNumberChecker(splitted[0], Integer.class)) {
                    if (Integer.valueOf(input) < 4) {
                        result = Integer.parseInt(splitted[0]);
                    }
                }

            } else if (Validator.CorrectNumberChecker(input, Integer.class)) {
                if (Integer.valueOf(input) < 4 && input.length()<2) {
                    result = Integer.parseInt(input);
                }
            } else {
                throw new WrongParameterException("That's not an amount of hearts marine can have!");
            }
            if (result > 0) {
                return result;
            } else {
                throw new WrongParameterException("There's always a heart.. Or two.. Or three underneath all that armour. Maybe.");
            }
        } catch (WrongParameterException | BlankRequestException ex) {
            consoleProcessor.printError(ex.toString());
            return heartCountRequest();
        }
    }

    public AstartesCategory astartesCategoryRequest() {
        String input = consoleProcessor.askAstartesCategory(AstartesCategory.values());
        String num;
        try {
            if (input.contains(" ")) {
                num = input.split(" ")[0];
                if (Validator.isNullChecker(num)) {
                    throw new WrongParameterException("Something's wrong with the number format.");
                }
            } else {
                num = input;
            }
            if (Validator.CorrectNumberChecker(num, Integer.class)) {
                if (Integer.parseInt(num) <= AstartesCategory.values().length && Integer.parseInt(num) >= 1) {
                    return AstartesCategory.values()[Integer.parseInt(num)-1];
                } else {
                    throw new WrongParameterException("Wrong number!");
                }
            } else {
                throw new WrongParameterException("Something's wrong with the number format.");
            }

        } catch (WrongParameterException | NumberFormatException ex) {
            consoleProcessor.printError(ex.toString());
            return astartesCategoryRequest();
        }
    }

    public Weapon weaponRequest() {
        String input = consoleProcessor.askWeaponType(Weapon.values());
        String num;
        try {
            if (input.contains(" ")) {
                num = input.split(" ")[0];
                if (Validator.isNullChecker(num)) {
                    throw new WrongParameterException("Something's wrong with the number format.");
                }
            } else {
                num = input;
            }
            if (Validator.CorrectNumberChecker(num, Integer.class)) {
                if (Integer.parseInt(num) <= Weapon.values().length && Integer.parseInt(num) >= 1) {
                    return Weapon.values()[Integer.parseInt(num)-1];
                } else {
                    throw new WrongParameterException("Wrong number!");
                }
            } else {
                throw new WrongParameterException("Something's wrong with the number format.");
            }

        } catch (WrongParameterException | NumberFormatException ex) {
            consoleProcessor.printError(ex.toString());
            return weaponRequest();
        }
    }

    public Chapter chapterRequest() {
        String name = consoleProcessor.ask("Enter chapter's name: ");
        String parentlegion = consoleProcessor.ask("Enter chapter's parent legion: ");
        try {
            if (Validator.ValidNameChecker(name) && Validator.ValidNameChecker(parentlegion)) {
                return new Chapter(name, parentlegion);
            } else {
                throw new WrongParameterException("Every marine has a chapter, dammit!");
            }
        } catch (WrongParameterException ex) {
            consoleProcessor.printError(ex.toString());
            return chapterRequest();
        }
    }

    public ConsoleProcessor getConsoleProcessor() {
        return consoleProcessor;
    }
}