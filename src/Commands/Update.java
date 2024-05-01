package Commands;

import Exceptions.NotFoundException;
import Exceptions.WrongParameterException;
import Interfaces.ExtendedCommand;
import Processors.Validator;
import SpaceMarines.*;


/**
 * Updates element of collection by id.
 * Offers user an opportunity to fill required fields
 */


public class Update extends AbstractCommand implements ExtendedCommand {
    public Update(String consoleName) {
        super(consoleName, "(long id) Update marine by id", "Marine is updated!");
    }

    @Override
    public void execute(String... params) throws WrongParameterException {
        try {
            SpaceMarine marine = collectionProcessor.getMarineById(Long.parseLong(params[0]));
            String name = marine.getName();
            Coordinates coordinates = marine.getCoordinates();
            float health = marine.getHealth();
            int heartCount = marine.getHeartCount();
            AstartesCategory category = marine.getAstartesCategory();
            Weapon weapon = marine.getWeaponType();
            Chapter chapter = marine.getChapter();


            String ans = collectionProcessor.getSender().getConsoleProcessor().askWhatToChange();
            if (Validator.IntegerChecker(ans)) {
                String[] splitted = ans.split(" ");
                int[] fieldnumbers = new int[splitted.length];
                for (int i = 0; i < fieldnumbers.length; i++) {
                    fieldnumbers[i] = Integer.parseInt(splitted[i]);
                }
                for (int num : fieldnumbers) {
                    if (num > 7) {
                        throw new WrongParameterException("There's no field with the number" + num);
                    }
                    switch (num) {
                        case 1 -> name = collectionProcessor.getSender().nameRequest();
                        case 2 -> coordinates = collectionProcessor.getSender().coordinatesRequest();
                        case 3 -> health = collectionProcessor.getSender().healthRequest();
                        case 4 -> heartCount = collectionProcessor.getSender().heartCountRequest();
                        case 5 -> category = collectionProcessor.getSender().astartesCategoryRequest();
                        case 6 -> weapon = collectionProcessor.getSender().weaponRequest();
                        case 7 -> chapter = collectionProcessor.getSender().chapterRequest();
                    }
                }
                SpaceMarine updatedMarine = new SpaceMarine(
                        marine.getId(),
                        name,
                        coordinates,
                        marine.getCreationDate(),
                        health,
                        heartCount,
                        category,
                        weapon,
                        chapter);
                collectionProcessor.setMarineById(Long.parseLong(params[0]), updatedMarine);
                sendRegards();
            } else throw new WrongParameterException("Only numbers and spaces are allowed in the string");
        } catch (NumberFormatException ex) {
            throw new WrongParameterException("Wrong parameter entered");
        } catch (NotFoundException ex) {
            collectionProcessor.getSender().getConsoleProcessor().printError(ex.toString());
            collectionProcessor.getSender().getConsoleProcessor().printAdvice("Enter id of an existing marine. Use 'show' for viewing Imperium Army list.");
        } catch (WrongParameterException ex) {
            collectionProcessor.getSender().getConsoleProcessor().printError(ex.toString());
            execute(params);
        }
    }
}
