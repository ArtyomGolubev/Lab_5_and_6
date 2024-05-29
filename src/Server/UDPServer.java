//package Server;
//
//import Other.Network.NetworkProvider;
//import Other.Network.Serializer;
//import Other.Responses.AbstractResponse;
//import Server.Processors.CommandProcessor;
//import Server.Processors.LoggingProcessor;
//import Server.Processors.RequestProcessor;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.SelectionKey;
//import java.nio.channels.Selector;
//import java.nio.channels.ServerSocketChannel;
//import java.nio.channels.SocketChannel;
//import java.nio.channels.spi.SelectorProvider;
//import java.util.Iterator;
//
//public class TCPServer implements NetworkProvider {
//    private static final int BUFFER_SIZE = 4096;
//    private ByteBuffer buffer;
//    private static final String HOST = "localhost";
//    private static final int PORT = 49446;
//
//    private ServerSocketChannel serverSocketChannel;
//    private Selector selector;
//    private CommandProcessor commandProcessor;
//    private RequestProcessor requestProcessor;
//    private LoggingProcessor loggingProcessor;
//
//    public TCPServer(CommandProcessor commandProcessor, RequestProcessor requestProcessor, LoggingProcessor loggingProcessor) {
//        this.commandProcessor = commandProcessor;
//        this.requestProcessor = requestProcessor;
//        this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
//        this.loggingProcessor = loggingProcessor;
//    }
//
//    @Override
//    public void openConnection() throws IOException {
//        this.serverSocketChannel = ServerSocketChannel.open();
//        this.serverSocketChannel.configureBlocking(false);
//        InetSocketAddress inetSocketAddress = new InetSocketAddress(HOST, PORT);
//        this.serverSocketChannel.bind(inetSocketAddress);
//        this.selector = initSelector();
//    }
//
//    @Override
//    public void run() {
//        try {
//            while (true) {
//                selector.selectNow();
//                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
//                while (selectedKeys.hasNext()) {
//                    SelectionKey key = takeKey(selectedKeys);
//                    processKey(key);
//                }
//            }
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
//    private SelectionKey takeKey(Iterator<SelectionKey> selectionKeyIterator) {
//        SelectionKey key = selectionKeyIterator.next();
//        selectionKeyIterator.remove();
//        return key;
//    }
//
//    private Selector initSelector() throws IOException {
//        Selector socketSelector = SelectorProvider.provider().openSelector();
//        this.serverSocketChannel.register(socketSelector, SelectionKey.OP_ACCEPT);
//        return socketSelector;
//    }
//
//    private void processKey(SelectionKey key) throws IOException {
//        if (key.isValid()) {
//            if (key.isAcceptable()) {
//                accept(key);
//            } else if (key.isReadable()) {
//                read(key);
//            } else if (key.isWritable()) {
//                write(key);
//            }
//        }
//    }
//
//    private void accept(SelectionKey key) throws IOException {
//        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
//        SocketChannel socketChannel = ssc.accept();
//        socketChannel.configureBlocking(false);
//        System.out.println("Connected: " + socketChannel.getRemoteAddress());
//        loggingProcessor.log("Connected: " + socketChannel.getRemoteAddress());
//        socketChannel.register(selector, SelectionKey.OP_READ);
//    }
//
//    private void read(SelectionKey key) throws IOException {
//        SocketChannel socketChannel = (SocketChannel) key.channel();
//        this.buffer.clear();
//        int bytesRead;
//        try {
//            bytesRead = socketChannel.read(this.buffer);
//        } catch (IOException e) {
//            key.cancel();
//            socketChannel.close();
//            return;
//        }
//
//        if (bytesRead == -1) {
//            key.cancel();
//            return;
//        }
//        this.buffer.flip();
//
//        AbstractResponse response = requestProcessor.processRequest(buffer);
//        System.out.println(response);
//        loggingProcessor.log("Sent: " + response.toString());
//
//        socketChannel.register(this.selector, SelectionKey.OP_WRITE, response);
//    }
//
//    public void close() throws IOException {
//        if (serverSocketChannel != null) {
//            serverSocketChannel.close();
//        }
//    }
//
//    private void write(SelectionKey key) throws IOException {
//        SocketChannel socketChannel = (SocketChannel) key.channel();
//        AbstractResponse response = (AbstractResponse) key.attachment();
//
//        ByteBuffer writeBuffer = Serializer.serializeObject(response);
//        writeBuffer.flip();
//        while (writeBuffer.hasRemaining()) {
//            socketChannel.write(writeBuffer);
//        }
//
//        socketChannel.register(selector, SelectionKey.OP_READ);
//    }
//}



package Server;

import Other.Network.NetworkProvider;
import Other.Network.Serializer;
import Other.Responses.AbstractResponse;
import Server.Processors.CommandProcessor;
import Server.Processors.LoggingProcessor;
import Server.Processors.RequestProcessor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class UDPServer implements NetworkProvider {
    private static final int BUFFER_SIZE = 4096;
    private ByteBuffer buffer;
    private static final String HOST = "localhost";
    private static final int PORT = 49446;

    private DatagramSocket datagramSocket;
    private CommandProcessor commandProcessor;
    private RequestProcessor requestProcessor;
    private LoggingProcessor loggingProcessor;

    public UDPServer(CommandProcessor commandProcessor, RequestProcessor requestProcessor, LoggingProcessor loggingProcessor) {
        this.commandProcessor = commandProcessor;
        this.requestProcessor = requestProcessor;
        this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.loggingProcessor = loggingProcessor;
    }

    @Override
    public void openConnection() throws IOException {
        this.datagramSocket = new DatagramSocket(new InetSocketAddress(HOST, PORT));
    }

    @Override
    public void run() {
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
                datagramSocket.receive(packet);
                processPacket(packet);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void processPacket(DatagramPacket packet) throws IOException {
        this.buffer.clear();
        this.buffer.put(packet.getData(), 0, packet.getLength());
        this.buffer.flip();

        AbstractResponse response = requestProcessor.processRequest(buffer);
        System.out.println(response);
        loggingProcessor.log("Sent: " + response.toString());

        ByteBuffer responseData = Serializer.serializeObject(response);
        byte[] arr = new byte[responseData.remaining()];
        DatagramPacket responsePacket = new DatagramPacket(arr, arr.length, packet.getAddress(), packet.getPort());
        datagramSocket.send(responsePacket);
    }

    public void close() throws IOException {
        if (datagramSocket != null) {
            datagramSocket.close();
        }
    }
}
