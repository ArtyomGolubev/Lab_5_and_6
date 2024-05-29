//package Other.Network;
//
//import Other.Requests.RequestDTO;
//import Other.Responses.AbstractResponse;
//import Other.Responses.ResponseDTO;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.nio.ByteBuffer;
//
//public class Serializer {
//
//    public static <T extends AbstractResponse> byte[] serializeObject(T response) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(baos);
//        oos.writeObject(new ResponseDTO(response));
//        oos.close();
//        byte[] data = baos.toByteArray();
//        return data;
//    }
//
//    public static RequestDTO deserializeObject(ByteBuffer data) throws IOException, ClassNotFoundException {
//        byte[] arr = new byte[data.remaining()];
//        try (ByteArrayInputStream bais = new ByteArrayInputStream(arr);
//             ObjectInputStream ois = new ObjectInputStream(bais)) {
//            return (RequestDTO) ois.readObject();
//        }
//    }
//}

package Other.Network;

import Other.Requests.RequestDTO;
import Other.Responses.AbstractResponse;
import Other.Responses.ResponseDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class Serializer {

//    public static <T extends AbstractResponse> byte[] serializeObject(T response) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(baos);
//        oos.writeObject(response);
//        oos.close();
//        byte[] data = baos.toByteArray();
//        return data;
//    }

    public static <T extends AbstractResponse> ByteBuffer serializeObject(T response) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(new ResponseDTO(response));
        oos.close();

        ByteBuffer buffer = ByteBuffer.allocate(baos.size());
        buffer.put(baos.toByteArray());
        return buffer;
    }

//    public static RequestDTO deserializeObject(ByteBuffer data) throws IOException, ClassNotFoundException {
//        byte[] arr = new byte[data.remaining()];
//        try (ByteArrayInputStream bais = new ByteArrayInputStream(arr);
//             ObjectInputStream ois = new ObjectInputStream(bais)) {
//            RequestDTO requestDTO = (RequestDTO) ois.readObject();
//            return requestDTO;
//        }
//    }

    public static RequestDTO deserializeObject(ByteBuffer buffer) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (RequestDTO) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
