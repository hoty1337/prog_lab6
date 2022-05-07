package ru.hoty.commands;

import java.nio.channels.SocketChannel;

import ru.hoty.collection.CollectionManager;
import ru.hoty.utils.AnswerManager;

/**
 * Class for printing an info of org.hoty.collection.
 */

public class Info implements CommandInterface {

    /**
     * Class constructor
     */
    public Info() {
        Help.addCmd("info", " - returns information about this org.hoty.collection.");
    }

    /**
     * Prints an info about org.hoty.collection
     */
    public boolean execute(SocketChannel sChannel, Object arg) {
        AnswerManager.addQueue(sChannel, CollectionManager.getInfo());
        return true;
    }
}
