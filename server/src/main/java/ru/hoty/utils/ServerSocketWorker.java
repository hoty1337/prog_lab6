package ru.hoty.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import ru.hoty.commands.Save;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.hoty.parser.CommandParser;

public class ServerSocketWorker {
    private Selector sel = null;
    private SelectionKey key = null;
    private final Logger logger;

    public ServerSocketWorker(int port) {
        logger = LogManager.getLogger();
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(port));
            sel = Selector.open();
            CommandParser.loadCommands();
			server.configureBlocking(false);
			server.register(sel, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    public Selector getSelector() {
        return sel;
    }

    public SelectionKey getSelectionKey() {
        return key;
    }

    public void setSelectionKey(SelectionKey key) {
        this.key = key;
    }

    public void accept() {
        try {
            ServerSocketChannel serv = (ServerSocketChannel) key.channel();
            SocketChannel client = serv.accept();
            //System.out.println("Клиент подключился: " + client.getRemoteAddress().toString());
            logger.info("Client connected: " + client.getRemoteAddress());

            client.configureBlocking(false);
            client.register(sel, SelectionKey.OP_READ);
        } catch(IOException e) {
            logger.error(e.getLocalizedMessage());;
        }
    }

    public void read() {
        Command cmd;
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        SocketChannel client = (SocketChannel) key.channel();
        try {
            int data = client.read(buffer);
            try {
                cmd = (Command) Serializer.deserialize(buffer.array());
                CommandManager.addQueue(client, cmd);
                logger.info("Received " + data + " bytes from " + client.getRemoteAddress() + "\n" + cmd.getCommand());
            } catch (ClassCastException | ClassNotFoundException e) {
                //System.out.println("Клиент вернул некорректный ответ.");
                logger.error(e.getLocalizedMessage());
                logger.warn("Client returned bad answer.");
            }
            client.configureBlocking(false);
            client.register(sel, SelectionKey.OP_WRITE);
        } catch(IOException e) {
            try {
                //System.out.println("Клиент " + client.getRemoteAddress().toString() + " отключился.");
                logger.info("Client disconnected: " + client.getRemoteAddress());
                key.cancel();
                new Save().execute(client, null);
            } catch (IOException e1) {
                logger.error(e1.getLocalizedMessage());
            }
        }
    }

    public void write(Answer obj) {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        SocketChannel client = (SocketChannel) key.channel();
        try {
            buffer = ByteBuffer.wrap(Serializer.serialize(obj));
            int data = client.write(buffer);
            if(obj.getAnswer().trim().length() > 0) {
                logger.info("Sent " + data + " bytes to " + client.getRemoteAddress() + "\n" + obj.getAnswer());
            }

            client.configureBlocking(false);
            client.register(sel, SelectionKey.OP_READ);
        } catch(IOException e) {
            try {
                //System.out.println("Клиент " + client.getRemoteAddress().toString() + " отключился.");
                logger.info("Client disconnected: " + client.getRemoteAddress());
                key.cancel();
                new Save().execute(client, null);
            } catch (IOException e1) {
                logger.error(e1.getLocalizedMessage());
            }
            //e.printStackTrace();
        }
    }
}
