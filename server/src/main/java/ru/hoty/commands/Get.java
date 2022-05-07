package ru.hoty.commands;

import java.nio.channels.SocketChannel;

import ru.hoty.collection.CollectionManager;
import ru.hoty.utils.AnswerManager;

/**
 * Class for printing an info of org.hoty.collection.
 */

public class Get implements CommandInterface {

    public boolean execute(SocketChannel sChannel, Object arg) {
        AnswerManager.addQueue(sChannel, CollectionManager.exist(Long.parseLong(arg.toString())) ? "1" : "");
        return true;
    }
}
