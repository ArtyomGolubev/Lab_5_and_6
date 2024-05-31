package Client;

import Client.ClientHandlers.*;
import Other.Requests.*;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 49446;

    public static void main(String[] args) throws IOException {

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Exit signal received (Ctrl+D).");
            }
        } );

        RequestHandler requestHandler = new RequestHandler(
                new ShowRequest("show"),
                new HelpRequest("help"),
                new InfoRequest("info"),
                new RemoveByIdRequest("remove_by_id"),
                new FilterLessThanHealthRequest("filter_less_than_health"),
                new ClearRequest("clear"),
                new ShuffleRequest("shuffle"),
                new AddRequest("add"),
                new AddRequest("remove_greater"),
                new AddRequest("remove_lower"),
                new UpdateRequest("update"),
                new RemoveAnyByHeartCountRequest("remove_by_heart_count"),
                new ExecuteScriptRequest("execute_script"),
                new FilterContainsNameRequest("filter_contains_name"),
                new ExitRequest("exit"));

        TCPClient tcpClient = new TCPClient(SERVER_ADDRESS, SERVER_PORT);
        Sender sender = new Sender(tcpClient);
        ResponseHandler responseHandler = new ResponseHandler();
        ConsoleHandler consoleHandler = new ConsoleHandler(requestHandler, sender, responseHandler);
        try {
            tcpClient.run();
            consoleHandler.takeInput();
        } catch (ConnectException ex) {
            consoleHandler.printError("Server is offline.");
        } catch (SocketTimeoutException ex) {
            consoleHandler.printError(ex.toString());
        }
    }
}