package Server.Processors;

import Client.ClientHandlers.Checker;
import Client.ClientHandlers.ConsoleMode;
import Other.Exceptions.WrongParameterException;
import Other.SpaceMarines.*;
import Server.Interfaces.FileHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

public class FileProcessor implements FileHandler {

    @Override
    public LinkedList<SpaceMarine> read(String fileName) {
        LinkedList<SpaceMarine> collection = new LinkedList<>();
        try {
            File file = new File(fileName);
            Scanner in = new Scanner(file, StandardCharsets.UTF_8.name());

            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] values = line.split(",");
                if (values.length == 1) {
                    continue;
                }
                try {
                    SpaceMarine marine = parseSpaceMarines(values);
                    collection.add(marine);
                } catch (Exception ex) {
                    System.out.println("Collection can't be processed.");
                    return null;
                }
            }
            System.out.println("Collection uploaded.");
            return collection;
        } catch (IOException ex) {
            System.out.println("File not found.");
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
                        Float.valueOf(marine.getHealth()).toString() + "," +
                        marine.getHeartCount().toString() + "," +
                        marine.getCategory().ordinal() + "," +
                        marine.getWeapon().ordinal() + "," +
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
        try {
            Long id = Long.parseLong(values[0]); //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
            LocalDate creationDate = LocalDate.parse(values[4]); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
            if (Checker.ValidNameChecker(values[1]) &&
                    Float.valueOf(values[2]) <= 138.0 &&
                    Checker.CorrectNumberChecker(values[2], Float.class) && Checker.CorrectNumberChecker(values[3], Float.class) &&
                    Float.valueOf(values[5]) > 0 && Checker.CorrectNumberChecker(values[5], Float.class) &&
                    Integer.valueOf(values[6]) > 0 && Integer.valueOf(values[6]) < 4 && Checker.CorrectNumberChecker(values[6], Integer.class) && !Checker.isNullChecker(values[6]) &&
                    Integer.valueOf(values[7]) > 0 && Integer.valueOf(values[7]) < 4 && Checker.CorrectNumberChecker(values[7], Integer.class) && !Checker.isNullChecker(values[7]) &&
                    Integer.valueOf(values[8]) > 0 && Integer.valueOf(values[8]) < 5 && Checker.CorrectNumberChecker(values[8], Integer.class) && !Checker.isNullChecker(values[8]) &&
                    !Checker.isNullChecker(values[9]) && !values[9].isEmpty()) {
                String name = values[1]; //Поле не может быть null, Строка не может быть пустой
                Coordinates coordinates = new Coordinates(Float.parseFloat(values[2]), Float.parseFloat(values[3])); //Поле не может быть null
                float health = Float.parseFloat(values[5]); //Значение поля должно быть больше 0
                Integer heartCount = Integer.parseInt(values[6]); //Поле не может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 3
                AstartesCategory category = AstartesCategory.values()[Integer.parseInt(values[7])]; //Поле может быть null
                Weapon weapon = Weapon.values()[Integer.parseInt(values[8])]; //Поле может быть null
                Chapter chapter = new Chapter(values[9], values[10]); //Поле не может быть null
                return new SpaceMarine(id, name, coordinates, creationDate, health, heartCount, category, weapon, chapter);
            } else throw new WrongParameterException("Collection can't be uploaded due to incorrect parameters");
        } catch (WrongParameterException ex) {
            System.out.println(ex);
            System.exit(666);
            return null;
        }
    }
}