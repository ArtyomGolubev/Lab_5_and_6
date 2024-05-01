import Commands.*;
import Exceptions.*;
import Interfaces.*;
import Processors.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, WrongParameterException, NonexistentCommandException, IncorrectFileNameException, NotFoundException, BlankRequestException {

        Scanner scanner = new Scanner(System.in);
        FileHandler fileProcessor = new FileProcessor();
        CommandProcessor commandProcessor = new CommandProcessor(fileProcessor);
        ConsoleProcessor consoleProcessor = new ConsoleProcessor(scanner, commandProcessor);
        Sender sender = new Sender(consoleProcessor);
        CollectionProcessor collectionProcessor = new CollectionProcessor(fileProcessor, sender, args[0]);
        commandProcessor.setCollectionProcessor(collectionProcessor);
        commandProcessor.addCommands (
                new Help("help"),
                new Info("info"),
                new Show("show"),
                new Add("add"),
                new Update("update"),
                new RemoveById("remove_by_id"),
                new Clear("clear"),
                new Save("save"),
                new ExecuteScript("execute_script"),
                new Exit("exit"),
                new Shuffle("shuffle"),
                new RemoveGreater("remove_greater"),
                new RemoveLower("remove_lower"),
                new RemoveAnyByHeartCount("remove_any_by_heart_count"),
                new FilterContainsName("filter_contains_name"),
                new FilterLessThanHealth("filter_less_than_health"));

        consoleProcessor.getInput();
    }
}