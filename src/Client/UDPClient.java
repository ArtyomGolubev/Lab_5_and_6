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

import Other.Exceptions.ConnectionFailedException;
import Other.Exceptions.ExitFailedException;
import Other.Network.NetworkProvider;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient implements NetworkProvider {
    private final String host;
    private final int port;
    private DatagramSocket datagramSocket;

    public UDPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() throws IOException {
        connect();
    }

    @Override
    public void openConnection() {
        try {
            connect();
        } catch (IOException ex) {
            throw new ConnectionFailedException("Connection failed.");
        }
    }

    private void connect() throws IOException {
        this.datagramSocket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(this.host);
        this.datagramSocket.connect(address, this.port);
    }

    public void send(DatagramPacket data) throws IOException {
        InetAddress address = InetAddress.getByName(this.host);
        DatagramPacket packet = new DatagramPacket(data.getData(), data.getLength(), address, this.port);
        datagramSocket.send(packet);
    }

    public DatagramPacket receive() throws IOException {
        byte[] buffer = new byte[4096];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(packet);
        return packet;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void closeConnection() throws ExitFailedException {
        if (datagramSocket != null) {
            datagramSocket.close();
        }
    }
}
