package ray1024.projects.collectioncontroller.client;


import ray1024.projects.collectioncontroller.general.communication.IConnector;
import ray1024.projects.collectioncontroller.general.communication.IRequest;
import ray1024.projects.collectioncontroller.general.communication.IResponse;
import ray1024.projects.collectioncontroller.general.tools.Serializer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientConnector implements IConnector {
    private final Socket socket;
    private final SocketChannel socketChannel;
    private final ByteBuffer sizeBufferIn;
    private final ByteBuffer sizeBufferOut;
    private ByteBuffer objectBufferIn;
    private ByteBuffer objectBufferOut;

    private int objectSizeIn;
    private int objectSizeOut;
    private long lastActionTime;

    public ClientConnector(InetAddress serverAddress, int port) {
        try {
            socket = SocketChannel.open().socket();
            socket.connect(new InetSocketAddress(serverAddress, port));
            lastActionTime = System.currentTimeMillis();
        } catch (IOException e) {
            throw new RuntimeException("SOCKET_CONNECTOR_ERROR");
        }
        socketChannel = socket.getChannel();
        try {
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            throw new RuntimeException("CONNECTOR_SOCKET_SET_BLOCKING_ERROR");
        }
        sizeBufferIn = ByteBuffer.allocate(4).clear();
        sizeBufferOut = ByteBuffer.allocate(4).clear();
        objectSizeIn = -1;
        objectSizeOut = -1;
    }

    @Override
    public IConnector sendRequest(IRequest request) {
        try {
            byte[] buff = Serializer.serialize(request);
            if (buff == null) throw new IOException("ksdjsncio;,vlmkdv");
            objectSizeOut = buff.length;
            sizeBufferOut.clear();
            sizeBufferOut.putInt(objectSizeOut);
            ByteBuffer byteBuffer = ByteBuffer.wrap(buff);
            sizeBufferOut.clear();
            socketChannel.write(sizeBufferOut);
            socketChannel.write(byteBuffer);
            lastActionTime = System.currentTimeMillis();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return this;
    }

    @Override
    public IRequest receiveRequest() {
        return null;
    }

    @Override
    public IResponse receiveResponse() {
        try {
            if (sizeBufferIn.remaining() == 0) {
                if (objectSizeIn == -1) {
                    sizeBufferIn.clear();
                    objectSizeIn = sizeBufferIn.getInt();
                    if (objectSizeIn > 0) objectBufferIn = ByteBuffer.allocate(objectSizeIn);
                    objectBufferIn.clear();
                }
                if (objectBufferIn.remaining() > 0) socketChannel.read(objectBufferIn);
                if (objectBufferIn.remaining() == 0) {
                    sizeBufferIn.clear();
                    objectSizeIn = -1;
                    objectBufferIn.clear();
                    lastActionTime = System.currentTimeMillis();
                    return (IResponse) Serializer.deserialize(objectBufferIn.array());
                }
            } else {
                socketChannel.read(sizeBufferIn);
                if (objectBufferIn != null) objectBufferIn.clear();
                objectSizeIn = -1;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public IConnector sendResponse(IResponse response) {
        return this;
    }

    @Override
    public boolean isConnected() {
        if (!isNoise()) {
            disconnect();
            return false;
        }
        return true;
    }

    @Override
    public void disconnect() {
        try {
            socket.close();
        } catch (Throwable ignored) {
        }
    }

    @Override
    public boolean isNoise() {
        return System.currentTimeMillis() - lastActionTime <= 60000;
    }
}
