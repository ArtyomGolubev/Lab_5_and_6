package Client.ClientHandlers;

import Client.UDPClient;
import Other.Exceptions.ExitFailedException;
import Other.Requests.AbstractRequest;
import Other.Requests.ExitRequest;
import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.ExitResponse;
import Other.Responses.ResponseDTO;

import java.io.*;

public class Sender {
    private final UDPClient client;

    public Sender(UDPClient client) {
        this.client = client;
    }

    public <T extends AbstractRequest> AbstractResponse sendRequest(T request) throws IOException, ExitFailedException, ClassNotFoundException {
        if (request instanceof ExitRequest) {
            return handleExitRequest(request);
        }
        sendObject(request);
        return receiveResponse();
    }

    private <T extends AbstractRequest> AbstractResponse handleExitRequest(T request) throws IOException, ExitFailedException {
        try {
            client.closeConnection();
            return new ExitResponse("EXIT", "EXIT");
        } catch (IOException ex) {
            throw new ExitFailedException(ex);
        }
    }

    private void sendObject(AbstractRequest request) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        RequestDTO requestDTO = new RequestDTO(request);
        oos.writeObject(requestDTO);
        oos.close();
        client.send(baos.toByteArray());
    }

    private AbstractResponse receiveResponse() throws IOException, ClassNotFoundException {
        byte[] responseBytes = client.receive();
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(responseBytes))) {
            ResponseDTO responseDTO = (ResponseDTO) ois.readObject();
            return responseDTO.getAbstractResponse();
        }
    }
}