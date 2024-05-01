package Interfaces;

import Exceptions.*;

public interface ExtendedCommand {
    void execute(String... params) throws WrongParameterException;
}