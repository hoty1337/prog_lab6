package ru.hoty.commands;

import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.stream.Collectors;
import ru.hoty.collection.CollectionManager;
import ru.hoty.collection.Ticket;
import ru.hoty.utils.AnswerManager;
import ru.hoty.utils.TextFormatter;

/**
 * Class for removing all elements which lower than this.
 */
public class RemoveLower implements CommandInterface {

    /**
     * Class constructor
     */
    public RemoveLower() {
        Help.addCmd("remove_lower [Long]", " - removes all elements which lower than this.");
    }

    /**
     * Gets the ID of element, then removes all which lower than this.
     * 
     * @return true on success and false if not.
     */
    public boolean execute(SocketChannel sChannel, Object arg) {
        Long uId;
        // AnswerManager.addQueue(sChannel, "Введите ID, элементы с ценой ниже которого будут удалены:");
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
        if(!CollectionManager.exist(uId)) {
            AnswerManager.addQueue(sChannel, TextFormatter.toRed("Element with key " + uId + " not found."));
            return false;
        }
        final Ticket tempTicket = CollectionManager.get(uId);
        AnswerManager.addQueue(sChannel, "Deleted elements:");
        Set<Long> keys = CollectionManager.values().stream()
                .filter(a -> tempTicket.compareTo(a) > 0)
                .map(Ticket::getId)
                .collect(Collectors.toSet());
        for (Long k : keys) {
            AnswerManager.addQueue(sChannel, "ID = " + k);
        }
        if(!CollectionManager.removeKey(keys)) {
            AnswerManager.addQueue(sChannel, TextFormatter.toYellow("\nNothing was deleted."));
        }

        return true;
    }
}
