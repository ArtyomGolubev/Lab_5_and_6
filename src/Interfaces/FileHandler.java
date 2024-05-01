package Interfaces;

import Processors.ConsoleProcessor;
import SpaceMarines.SpaceMarine;
import java.util.LinkedList;

public interface FileHandler {
    LinkedList<SpaceMarine> read( String fileName, ConsoleProcessor consoleProcessor);

    void write(LinkedList<SpaceMarine> collection, String fileName);
}