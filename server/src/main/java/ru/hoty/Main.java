package ru.hoty;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import ru.hoty.collection.CollectionManager;
import ru.hoty.commands.Save;
import ru.hoty.parser.CommandParser;
import ru.hoty.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
		final int PORT = 1337;
		final String FILE_ENV = "FILE_PATH";
		final ConsoleWorker consoleWorker = new ConsoleWorker(new Scanner(System.in));

		if(!CollectionManager.setFilePath(FILE_ENV) ||
			!CollectionManager.loadCollection()) {
			logger.fatal("Can't load collection file on the path " + System.getenv(FILE_ENV));
			System.exit(0);
		}
        try {
			ServerSocketWorker sWorker = new ServerSocketWorker(PORT);
			logger.info("Server is running on port: " + PORT);
			while(true) {
				int readyChannels = sWorker.getSelector().select(1000);
				String serverCmd = consoleWorker.checkConsoleInput();
				if(serverCmd != null && serverCmd.equals("save")) {
					new Save().execute();
				}
				if(readyChannels == 0) continue;


				Set<SelectionKey> keys = sWorker.getSelector().selectedKeys();
				for(Iterator<SelectionKey> iter = keys.iterator(); iter.hasNext(); ) {
					SelectionKey key = iter.next();
					sWorker.setSelectionKey(key);
					iter.remove();
					if(key.isValid() && key.isAcceptable()) {
						sWorker.accept();

					}
					if(key.isValid() && key.isReadable()) {
						SocketChannel client = (SocketChannel) key.channel();
						sWorker.read();
						while(!CommandManager.isEmpty(client)) {
							Command cmd = CommandManager.getCommand(client);
							if(cmd == null || CommandParser.executeCmd(client, cmd.getCommand(), cmd.getArgument()) == -1) {
								AnswerManager.addQueue(client, "Command not found!");
							}
						}
					}
					if(key.isValid() && key.isWritable()) {
						SocketChannel client = (SocketChannel) key.channel();
						String str = AnswerManager.getString(client);
						while(str != null) {
							sWorker.write(new Answer(str));
							Thread.sleep(10);
							str = AnswerManager.getString(client);
						}
						sWorker.write(new Answer("\0"));
					}
				}
			}
        } catch (InterruptedException | IOException e) {
			logger.error(e.getLocalizedMessage());
		}
    }
}
