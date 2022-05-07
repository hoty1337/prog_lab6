package ru.hoty.commands;

import java.nio.channels.SocketChannel;

import ru.hoty.collection.CollectionManager;
import ru.hoty.utils.AnswerManager;
import ru.hoty.utils.TextFormatter;

/**
 * Class for printing all of elements.
 */
public class Show implements CommandInterface {

    /**
     * Class constructor
     */
    public Show() {
        Help.addCmd("show", " - prints all of elements.");
    }

    /**
     * Prints all of elements.
     * 
     * @return true on success and false if not.
     */
    public boolean execute(SocketChannel sChannel, Object arg) {
        try {
            if(CollectionManager.getSize() == 0) {
                AnswerManager.addQueue(sChannel, TextFormatter.toYellow("Collection is empty."));
                return true;
            }
            CollectionManager.entrySet().stream().sorted((a, b) -> 
                {return a.getValue().compareTo(b.getValue());}).forEach(a -> AnswerManager.addQueue(sChannel, a.getValue().toString()));
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
