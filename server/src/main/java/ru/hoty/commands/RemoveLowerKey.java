package ru.hoty.commands;

import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import ru.hoty.collection.CollectionManager;
import ru.hoty.collection.Ticket;
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
        AnswerManager.addQueue(sChannel, "Removed elements:");
        Set<Long> keysToDelete = CollectionManager.values().stream()
                .map(Ticket::getId)
                .filter(id -> id < uId)
                .collect(Collectors.toSet());
        if(!keysToDelete.isEmpty()) {
            CollectionManager.removeKey(keysToDelete);
            for (Long id : keysToDelete) {
                AnswerManager.addQueue(sChannel, "ID: " + id + " ");
            }
        } else {
            AnswerManager.addQueue(sChannel, TextFormatter.toYellow("\nNothing was deleted."));
        }
        
        return true;
    }
}
