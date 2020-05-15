package application;

import communication.Request;
import communication.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

public class HandlerClient {
    public final int SIZE_BUFFER = 100000;

    private SocketAddress socketAddress;
    private DatagramChannel datagramChannel;

    public void bind(int port) throws IOException {
        socketAddress = new InetSocketAddress(port);
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(socketAddress);
    }

    protected void send(byte[] bytes) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        datagramChannel.send(byteBuffer, socketAddress);
    }

    public void sendResponses(ArrayList<Response> responses) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(responses);
        objectOutputStream.flush();
        objectOutputStream.close();
        send(byteArrayOutputStream.toByteArray());
    }

    protected byte[] receive(int delay) throws IOException {
        long startTime = System.currentTimeMillis();
        ByteBuffer byteBuffer = ByteBuffer.allocate(SIZE_BUFFER);
        do {
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime >= delay) {
                return new byte[0];
            }
            socketAddress = datagramChannel.receive(byteBuffer);
        } while (socketAddress == null);
        byteBuffer.flip();
        byte[] data = new byte[byteBuffer.limit()];
        byteBuffer.get(data, 0, byteBuffer.limit());
        return data;
    }

    public ArrayList<Request> receiveRequests(int delay) throws IOException, ClassNotFoundException {
        byte[] bytes = receive(delay);
        if(bytes.length == 0) {
            return new ArrayList<>();
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        ArrayList<Request> requests = (ArrayList<Request>) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return requests;
    }

}
