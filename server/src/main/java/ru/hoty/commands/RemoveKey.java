package ru.hoty.commands;

import java.nio.channels.SocketChannel;

import ru.hoty.collection.CollectionManager;
import ru.hoty.utils.AnswerManager;
import ru.hoty.utils.TextFormatter;

/**
 * Class for removing element by key.
 */
public class RemoveKey implements CommandInterface {

    /**
     * Class constructor
     */
    public RemoveKey() {
        Help.addCmd("remove_key [Long]", " - removes element by key.");
    }

    /**
     * Gets the ID of element, then removes it.
     * 
     * @return true on success and false if not.
     */
    public boolean execute(SocketChannel sChannel, Object arg) {
        Long key = null;
        if(arg == null) {
            AnswerManager.addQueue(sChannel, TextFormatter.toYellow("You should enter ID:"));
            return false;
        }
        try {
            key = Long.parseLong((String) arg);
        } catch (Exception e) {
            AnswerManager.addQueue(sChannel, TextFormatter.toRed("ID should be numeric!"));
            return false;
        }
        if(key <= 0) {
            AnswerManager.addQueue(sChannel, TextFormatter.toRed("ID should be positive!"));
            return false;
        }
        if(!CollectionManager.removeKey(key)) {
            AnswerManager.addQueue(sChannel, TextFormatter.toRed("Element with key " + key + " not found."));
            return false;
        }
        AnswerManager.addQueue(sChannel, TextFormatter.toGreen("Element has been successfully removed."));
        return true;
    }
}
