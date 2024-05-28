package Server.Processors;

import Client.ClientHandlers.Checker;
import Other.Exceptions.NotFoundException;
import Other.SpaceMarines.SpaceMarine;
import Server.Interfaces.FileHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for making requests to user and managing the collection.
 */

public class CollectionProcessor {
    private LinkedList<SpaceMarine> collection;
    private String collectionFileName;
    private String data;
    private LocalDate lastUpdateDate;
    private FileHandler fileHandler;

    public CollectionProcessor(FileHandler fileHandler, String fileName) {
        this.fileHandler = fileHandler;
        lastUpdateDate = LocalDate.now();
        loadFromFile(fileName);
        updateData();
    }

    public void loadFromFile(String fileName) {
        this.collection = fileHandler.read(fileName);
        this.collectionFileName = fileName;
        if (collection != null) {
            //try {
                Collections.sort(collection);
//            } catch (NullPointerException ex) {
//                System.out.println(ex);
//            }
        }
    }

    private void updateData() {
        data = "Collection type: " + LinkedList.class.getName() + "\n"
                + "Elements type: " + SpaceMarine.class.getName() + "\n"
                + "Amount of elements: " + collection.size() + "\n"
                + "Last update date: " + lastUpdateDate;
    }

    public String getData() {
        return data;
    }

    public void addNewMarine(SpaceMarine marine) {
            collection.add(marine);
            this.lastUpdateDate = LocalDate.now();
            updateData();
    }

    public void clearCollection() {
        this.collection.clear();
    }

    public LinkedList<SpaceMarine> getMarinesByName(String substring) {
        return collection.stream()
                .filter(marine -> Checker.isSubstringChecker(substring, marine.getName()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public void removeById (long id) throws NotFoundException {
        int len = this.collection.size();
        collection.removeIf(m -> m.getId() == id);
        if (len < this.collection.size()) {
            this.lastUpdateDate = LocalDate.now();
            updateData();
        } else {
            throw new NotFoundException("No marine with such id found.");
        }
    }

    public LinkedList<SpaceMarine> getCollection() {
            return collection;
    }

    public String getCollectionFileName() {
        return collectionFileName;
    }

    public SpaceMarine getMarineById (Long id) throws NotFoundException {
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
        this.lastUpdateDate = LocalDate.now();
        updateData();
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
}
