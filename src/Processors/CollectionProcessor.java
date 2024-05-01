package Processors;

import java.util.*;
import SpaceMarines.*;
import java.time.LocalDate;
import Interfaces.FileHandler;
import Exceptions.NotFoundException;


/**
 * Class for making requests to user and managing the collection.
 */


public class CollectionProcessor {
    private String fileName;
    private LinkedList<SpaceMarine> collection;
    private String collectionFileName;
    private String data;
    private LocalDate lastUpdateDate;
    private FileHandler fileHandler;
    private final Sender sender;

    public CollectionProcessor(FileHandler fileHandler, Sender sender, String fileName) {
        this.fileHandler = fileHandler;
        lastUpdateDate = LocalDate.now();
        this.sender = sender;
        this.fileName = fileName;
        loadFromFile(fileName);
        updateData();
    }

    public CollectionProcessor(FileHandler fileHandler, Sender sender) {
        this.fileHandler = fileHandler;
        lastUpdateDate = LocalDate.now();
        this.sender = sender;
        loadFromFile(fileName);
        updateData();
    }


    public void loadFromFile() {
        String fileName = sender.getConsoleProcessor().requestFilePath();
        this.collection = fileHandler.read(fileName, sender.getConsoleProcessor());
        if (collection == null) {
            loadFromFile();
        }
        this.collectionFileName = fileName;
        if (collection != null) {
            Collections.sort(collection);
        }
    }

    public void loadFromFile(String fileName) {
        this.collection = fileHandler.read(fileName, sender.getConsoleProcessor());
        if (collection == null) {
            loadFromFile();
        }
        this.collectionFileName = fileName;
        if (collection != null) {
            Collections.sort(collection);
        }
    }

    private void updateData() {
        data = "Collection type: " + LinkedList.class.getName() + "\n"
                + "Elements type: " + SpaceMarine.class.getName() + "\n"
                + "Amount of elements: " + collection.size() + "\n"
                + "Last update date: " + lastUpdateDate;
    }

    public void addNewMarine(SpaceMarine marine) {
            collection.add(marine);
            this.lastUpdateDate = LocalDate.now();
            updateData();
    }

    public SpaceMarine marineCreationFromInput() {
        return new SpaceMarine(
                        (long) (Math.random() * Long.MAX_VALUE),
                        sender.nameRequest(),
                        sender.coordinatesRequest(),
                        LocalDate.now(),
                        sender.healthRequest(),
                        sender.heartCountRequest(),
                        sender.astartesCategoryRequest(),
                        sender.weaponRequest(),
                        sender.chapterRequest());
    }

    public void marineCreationFromFile(String...params) {
        SpaceMarine marine = new SpaceMarine(
                        (long) (Math.random() * Long.MAX_VALUE),
                        params[0],
                        new Coordinates(Float.parseFloat(params[1]), Float.parseFloat(params[2])),
                        LocalDate.now(),
                        Float.parseFloat(params[3]),
                        Integer.parseInt(params[4]),
                        AstartesCategory.values()[Integer.parseInt(params[5]) - 1],
                        Weapon.values()[Integer.parseInt(params[6]) - 1],
                        new Chapter(
                                params[7],
                                params[8]));
        collection.add(marine);
        lastUpdateDate = LocalDate.now();
    }

    public void clearCollection() {
        this.collection.clear();
    }

    public SpaceMarine[] getMarinesByName(String substring) {
        List<SpaceMarine> marinesbyname = new ArrayList<>();
        for (SpaceMarine marine : collection) {
            if (Validator.isSubstringChecker(substring, marine.getName())) {
                marinesbyname.add(marine);
            }
        }
        return marinesbyname.toArray(new SpaceMarine[0]);
    }

    public String getData() {
        return data;
    }

    public void removeMarineById (long id) throws NotFoundException {
        collection.remove(getMarineById(id));
        lastUpdateDate = LocalDate.now();
    }

    public LinkedList<SpaceMarine> getCollection() {
            return collection;
    }

    public String getCollectionFileName() {
        return collectionFileName;
    }

    public SpaceMarine getMarineById (long id) throws NotFoundException {
        for (SpaceMarine marine : collection) {
            if (marine.getId() == id) return marine;
        }
        throw new NotFoundException("No marine with such id.");
    }

    public SpaceMarine[] getMarinesWithLessThanCertainHealth(float health) {
        List<SpaceMarine> marineswithlessthancertainhealth = new ArrayList<>();
        for (SpaceMarine marine : collection) {
            if (marine.getHealth() < health) {
                marineswithlessthancertainhealth.add(marine);
            }
        }
        return marineswithlessthancertainhealth.toArray(new SpaceMarine[0]);
    }

    public void shuffleCollection() {
        Collections.shuffle(collection);
        lastUpdateDate = LocalDate.now();
    }

    public SpaceMarine[] getMarinesByHeartCount(int heartCount) {
        List<SpaceMarine> marinesbyheartcount = new ArrayList<>();
        for (SpaceMarine marine : collection) {
            if (marine.getHeartCount() == heartCount) {
                marinesbyheartcount.add(marine);
            }
        }
        return marinesbyheartcount.toArray(new SpaceMarine[0]);
    }

    public void setMarineById(long id, SpaceMarine marine) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getId() == id) {
                collection.set(i, marine);
                break;
            }
        }
    }


    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public Sender getSender () {
        return sender;
    }
}
