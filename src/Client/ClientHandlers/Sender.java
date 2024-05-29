package Client.ClientHandlers;

import Client.UDPClient;
import Other.Exceptions.ExitFailedException;
import Other.Requests.AbstractRequest;
import Other.Requests.ExitRequest;
import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.ErrorResponse;
import Other.Responses.ExitResponse;
import Other.Responses.ResponseDTO;

import java.io.*;
import java.net.DatagramPacket;

public class Sender {
    private final UDPClient client;

    public Sender(UDPClient client) {
        this.client = client;
    }

    public <T extends AbstractRequest> AbstractResponse sendRequest(T request) throws IOException {
        if (request instanceof ExitRequest) {
            try {
                client.closeConnection();
                return new ExitResponse("exit", "exit");
            } catch (ExitFailedException e) {
                return new ErrorResponse(e.toString());
            }
        }
        sendObject(request);
        try {
            ResponseDTO responseDTO = (ResponseDTO) receiveObject();
            return responseDTO.getAbstractResponse();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends AbstractRequest> void sendObject(T request) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        RequestDTO requestDTO = new RequestDTO(request);
        oos.writeObject(requestDTO);
        oos.close();

        client.send(new DatagramPacket(baos.toByteArray(), baos.size()));
    }

    private Object receiveObject() throws IOException, ClassNotFoundException {
        DatagramPacket packet = client.receive();
        byte[] data = packet.getData();
        int length = packet.getLength();

        ByteArrayInputStream bais = new ByteArrayInputStream(data, 0, length);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }
}