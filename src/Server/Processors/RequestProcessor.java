package Server.Processors;

import Other.Network.Serializer;
import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Server.Commands.AbstractCommand;

import java.nio.ByteBuffer;

public class RequestProcessor {
    private final CommandProcessor commandProcessor;

    public RequestProcessor(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    public <T extends AbstractResponse> T processRequest(ByteBuffer buffer) {
        T response;
        RequestDTO requestDTO;
        try {
            requestDTO = Serializer.deserializeObject(buffer);
            AbstractCommand command = commandProcessor.getCommands().get(requestDTO.getAbstractRequest().getCommandName());
            System.out.println("Received: " + requestDTO.getAbstractRequest());
            response = (T) command.execute(requestDTO);
            return response;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}