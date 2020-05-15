package application;

import communication.Request;
import communication.Response;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class HandlerServer {
    public final int SIZE_BUFFER = 100000;

    private SocketAddress socketAddress;
    private DatagramSocket datagramSocket;

    public void connect(String host, int port) throws SocketException {
        socketAddress = new InetSocketAddress(host, port);
        datagramSocket = new DatagramSocket();
    }

    private void send(byte[] bytes) throws IOException {
        datagramSocket.send(new DatagramPacket(bytes, bytes.length, socketAddress));
    }

    public void sendRequests(ArrayList<Request> requests) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(requests);
        objectOutputStream.flush();
        objectOutputStream.close();
        send(byteArrayOutputStream.toByteArray());
    }

    private byte[] receive(int delay) throws IOException {
        byte[] bytes = new byte[SIZE_BUFFER];
        datagramSocket.setSoTimeout(delay);
        datagramSocket.receive(new DatagramPacket(bytes, bytes.length));
        return bytes;
    }

    public ArrayList<Response> receiveResponse(int delay) throws IOException, ClassNotFoundException {
        byte[] bytes = receive(delay);
        if (bytes.length == 0) {
            return new ArrayList<>();
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        ArrayList<Response> responses = (ArrayList<Response>) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return responses;
    }
}