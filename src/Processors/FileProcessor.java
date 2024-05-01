package Processors;

import SpaceMarines.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

public class FileProcessor implements Interfaces.FileHandler {

    @Override
    public LinkedList<SpaceMarine> read(String fileName, ConsoleProcessor consoleProcessor) {
        LinkedList<SpaceMarine> collection = new LinkedList<>();
        File file = new File(fileName);
        try (Scanner in = new Scanner(file, StandardCharsets.UTF_8.name())){
            if (!in.hasNextLine()) throw new NullPointerException("File is empty.");
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] values = line.split(",");
                try {
                    collection.add(parseSpaceMarines(values));
                } catch (Exception ex) {
                    consoleProcessor.printError("Collection can't be processed");
                    return null;
                }
            }
            consoleProcessor.println("Collection uploaded.");
            consoleProcessor.printAdvice("Use 'help' for viewing the command list.");
            return collection;
        } catch (NullPointerException err) {
            consoleProcessor.printError(err.toString());
            return null;
        } catch (IOException err) {
            consoleProcessor.printError("No file with such name found.");
            return null;
        }
    }

    @Override
    public void write(LinkedList<SpaceMarine> collection, String fileName) {
        String line;
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            for (SpaceMarine marine : collection) {
                line = marine.getId().toString() + "," +
                        marine.getName() + "," +
                        marine.getCoordinates().getX() + "," +
                        marine.getCoordinates().getY() + "," +
                        marine.getCreationDate().toString() + "," +
                        marine.getHealth() + "," +
                        marine.getHeartCount().toString() + "," +
                        marine.getAstartesCategory().ordinal() + "," +
                        marine.getWeaponType().ordinal() + "," +
                        marine.getChapter().getChapterName() + "," +
                        marine.getChapter().getParentLegion();
                out.write(line + "\n");
            }
            out.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private SpaceMarine parseSpaceMarines(String[] values) {
        Long id = Long.parseLong(values[0]); //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        String name = values[1]; //Поле не может быть null, Строка не может быть пустой
        Coordinates coordinates = new Coordinates(Float.parseFloat(values[2]), Float.parseFloat(values[3])); //Поле не может быть null
        java.time.LocalDate creationDate = LocalDate.parse(values[4]); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        Float health = Float.parseFloat(values[5]); //Значение поля должно быть больше 0
        Integer heartCount = Integer.parseInt(values[6]); //Поле не может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 3
        AstartesCategory category = AstartesCategory.values()[Integer.parseInt(values[7])]; //Поле может быть null
        Weapon weaponType = Weapon.values()[Integer.parseInt(values[8])]; //Поле может быть null
        Chapter chapter = new Chapter(values[9], values[10]); //Поле не может быть null
        return new SpaceMarine(id, name, coordinates, creationDate, health, heartCount, category, weaponType, chapter);
    }
}