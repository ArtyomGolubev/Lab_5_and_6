//package Client;
//
//import Other.Exceptions.ConnectionFailedException;
//import Other.Exceptions.ExitFailedException;
//import Other.Network.NetworkProvider;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//
//public class TCPClient implements NetworkProvider {
//    private final String host;
//    private final int port;
//    private Socket socket;
//
//    private InputStream inputStream;
//    private OutputStream outputStream;
//
//    public TCPClient(String host, int port) {
//        this.host = host;
//        this.port = port;
//    }
//
//    @Override
//    public void run() throws IOException {
//        connect();
//    }
//
//    @Override
//    public void openConnection() {
//        try {
//            connect();
//        } catch (IOException ex) {
//            throw new ConnectionFailedException("Connection failed.");
//        }
//    }
//
//    private void connect() throws IOException {
//        this.socket = new Socket();
//        socket.connect(new InetSocketAddress(this.host, this.port));
//        this.inputStream = socket.getInputStream();
//        this.outputStream = socket.getOutputStream();
//    }
//
//    public void closeConnection() throws ExitFailedException {
//        try {
//            if (this.socket != null) {
//                this.socket.close();
//            }
//        } catch (IOException ex) {
//            throw new ExitFailedException("Connection closure failed.");
//        }
//    }
//
//    public InputStream getInputStream() {
//        return inputStream;
//    }
//
//    public OutputStream getOutputStream() {
//        return outputStream;
//    }
//}

package Client;

import Other.Network.NetworkProvider;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPClient implements NetworkProvider {
    private final String host;
    private final int port;
    private DatagramSocket socket;

    public UDPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() throws IOException {
        connect();
    }

    @Override
    public void openConnection() throws IOException {
        connect();
    }

    private void connect() throws IOException {
        this.socket = new DatagramSocket();
    }

    public void closeConnection() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public void send(byte[] data) throws IOException {
        DatagramPacket packet = new DatagramPacket(data, data.length, new InetSocketAddress(host, port));
        socket.send(packet);
    }

    public byte[] receive(int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return packet.getData();
    }
}