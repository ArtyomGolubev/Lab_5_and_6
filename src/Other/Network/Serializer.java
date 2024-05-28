package Other.Network;

import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.ResponseDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {

    public static <T extends AbstractResponse> byte[] serializeObject(T response) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(new ResponseDTO(response));
        oos.close();
        byte[] data = baos.toByteArray();
        return data;
    }

    public static RequestDTO deserializeObject(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (RequestDTO) ois.readObject();
        }
    }
}
