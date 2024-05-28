package Client.ClientHandlers;

import Client.TCPClient;
import Other.Exceptions.ExitFailedException;
import Other.Requests.AbstractRequest;
import Other.Requests.ExitRequest;
import Other.Requests.RequestDTO;
import Other.Responses.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Sender {
    private final TCPClient client;
    public Sender(TCPClient client) {
        this.client = client;
    }

    public <T extends AbstractRequest> AbstractResponse sendRequest(T request) throws IOException {
        if (request instanceof ExitRequest) {
            try {
                client.closeConnection();
                return new ExitResponse("EXIT", "EXIT");
            } catch (ExitFailedException ex) {
                return new ErrorResponse(ex.toString());
            }
        }
        sendObject(request);
        try {
            ResponseDTO responseDTO = (ResponseDTO) receiveObject();
            return responseDTO.getAbstractResponse();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public <T extends AbstractRequest> void sendObject(T request) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        RequestDTO requestDTO = new RequestDTO(request);
        oos.writeObject(requestDTO);
        oos.close();
        sendData(baos.toByteArray());
    }

    private void sendData(byte[] bytes) {
        try {
            client.getOutputStream().write(bytes);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Object receiveObject() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(this.client.getInputStream());
        return objectInputStream.readObject();
    }
}