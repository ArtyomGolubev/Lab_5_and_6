package Server;

import Server.Commands.*;
import Server.Interfaces.FileHandler;
import Server.Processors.*;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Exit signal received (Ctrl+D).");
            }
        } );
        FileHandler fileProcessor = new FileProcessor();
        CollectionProcessor collectionProcessor = new CollectionProcessor(fileProcessor, args[0]);
        CommandProcessor commandProcessor = new CommandProcessor(fileProcessor);
        commandProcessor.setCollectionProcessor(collectionProcessor);
        commandProcessor.addCommands(
                new Add("add"),
                new Clear("clear"),
                new Save("save"),
                new Show("show"),
                new Help("help"),
                new Exit("exit"),
                new RemoveById("remove_by_id"),
                new Update("update"),
                new Shuffle("shuffle"),
                new FilterContainsName("filter_contains_name"),
                new FilterLessThanHealth("filter_less_than_health"),
                new RemoveAnyByHeartCount("remove_any_by_heart_count"),
                new Info("info"),
                new RemoveGreater("remove_greater"),
                new RemoveLower("remove_lower"));
        RequestProcessor requestProcessor = new RequestProcessor(commandProcessor);
        UDPServer server = new UDPServer(commandProcessor, requestProcessor, new LoggingProcessor("journal.log"));

        try {
            server.openConnection();
            server.run();
        } finally {
            server.close();
        }
    }
}