package ru.hoty.utils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketWorker {
    private SocketChannel server = null;
    private final SocketAddress addr;

    public SocketWorker(String host, int port) {
        addr = new InetSocketAddress(host, port);
        connect();
    }

    public void connect() {
        if(server != null && server.isConnected()) {
            return;
        }
        try {
            server = SocketChannel.open();
            server.connect(addr);
            System.out.println(TextFormatter.toGreen("Успешно подключились к серверу."));
        } catch (ConnectException e) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.out.println(TextFormatter.toRed("Сервер недоступен."));
            connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public void close() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean write(Object obj) {
        if(!isAccessible()) {
            return false;
        }
        try {
            ByteBuffer buffer = ByteBuffer.wrap(Serializer.serialize(obj));
            server.write(buffer);
            //System.out.println("Отправлено " + data + " байт");
        } catch (IOException e) {
            System.out.println(TextFormatter.toRed("Потеряно соединение с сервером."));
            try {
                server.close();
            } catch (IOException ignored) {}
            return false;
        }
        return true;
    }

    public Answer read() {
        if(!isAccessible()) {
            return null;
        }
        try {
            try {
                Answer temp;
                Answer ans = new Answer("");
                do {
                    ByteBuffer buf = ByteBuffer.allocate(4096);
                    server.read(buf);
                    temp = (Answer) Serializer.deserialize(buf.array());
                } while(ans.addAnswer(temp.getAnswer()));
//                System.out.println("Принято " + data + " байт");
                return new Answer(ans.getAnswer().substring(0, ans.getAnswer().length() - 1));
            } catch (ClassNotFoundException e) {
                System.out.println("Сервер вернул некорректный ответ.");
                return null;
            }
        } catch (IOException e) {
            System.out.println(TextFormatter.toRed("Потеряно соединение с сервером."));
            try {
                server.close();
            } catch (IOException ignored) {}
            return null;
        }
    }

    public boolean isAccessible() {
        if(!server.isConnected()) {
            return false;
        }
        if(!server.isOpen()) {
            System.out.println(TextFormatter.toRed("Невозможно отправить команду."));
            return false;
        }
        return true;
    }
}
