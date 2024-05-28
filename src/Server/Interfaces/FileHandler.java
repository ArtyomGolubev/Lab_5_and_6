package Server.Interfaces;

import Other.SpaceMarines.SpaceMarine;
import java.util.LinkedList;

public interface FileHandler {
    LinkedList<SpaceMarine> read(String fileName);

    void write(LinkedList<SpaceMarine> collection, String fileName);
}