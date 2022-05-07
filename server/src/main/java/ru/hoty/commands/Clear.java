package ru.hoty.commands;

import java.nio.channels.SocketChannel;

import ru.hoty.collection.CollectionManager;
import ru.hoty.utils.AnswerManager;
import ru.hoty.utils.TextFormatter;

/**
 * Class for removing all elements from org.hoty.collection.
 */
public class Clear implements CommandInterface {

	/**
	 * Class constructor
	 */
	public Clear() {
		Help.addCmd("clear", " - removes all elements from org.hoty.collection.");
	}

	/**
	 * Clear the org.hoty.collection.
     * 
     * @return true on success and false if not.
	 */
    public boolean execute(SocketChannel sChannel, Object arg) {
		CollectionManager.clear();
		AnswerManager.addQueue(sChannel, TextFormatter.toGreen("Collection was cleared."));
        return true;
    }
}
