package ru.hoty.commands;

import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Set;

import ru.hoty.collection.CollectionManager;
import ru.hoty.utils.AnswerManager;
import ru.hoty.utils.TextFormatter;

/**
 * Class for removing all elements which key lower than this key.
 */
public class RemoveLowerKey implements CommandInterface {

    /**
     * Class constructor
     */
    public RemoveLowerKey() {
        Help.addCmd("remove_lower_key [Long]", " - removes all elements which key lower than this.");
    }

    /**
     * Gets the ID of element, then removes all elements which key lower than this.
     * 
     * @return true on success and false if not.
     */
    public boolean execute(SocketChannel sChannel, Object arg) {
        Long uId;
        // AnswerManager.addQueue(sChannel, "Введите ID, элементы ниже которого хотите удалить:");
        if(arg == null) {
            AnswerManager.addQueue(sChannel, TextFormatter.toYellow("You should enter ID:"));
            return false;
        }
        try {
            uId = Long.parseLong((String) arg);
        } catch (Exception e) {
            AnswerManager.addQueue(sChannel, TextFormatter.toRed("ID should be numeric!"));
            return false;
        }
        if(uId <= 0) {
            AnswerManager.addQueue(sChannel, TextFormatter.toRed("ID should be positive!"));
            return false;
        }
        Set<Long> keySet = CollectionManager.keySet();
        Set<Long> keysToDelete = new HashSet<>();
        boolean isDeleted = false;
        AnswerManager.addQueue(sChannel, "Удалены элементы:");
        for (Long t : keySet) {
            if(t < uId) {
                keysToDelete.add(t);
                AnswerManager.addQueue(sChannel, TextFormatter.toYellow("ID = " + t));
                isDeleted = true;
            }
        }
        CollectionManager.removeKey(keysToDelete);
        if(!isDeleted) {
            AnswerManager.addQueue(sChannel, TextFormatter.toYellow("\nНи один элемент не был удален."));
        }
        
        return true;
    }
}
