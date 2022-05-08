package ru.hoty.commands;

import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Set;
import ru.hoty.collection.CollectionManager;
import ru.hoty.collection.Ticket;
import ru.hoty.collection.Venue;
import ru.hoty.utils.AnswerManager;

/**
 * Class for printing all of Venue sorted in ascending order.
 */
public class PrintFieldAscendingVenue implements CommandInterface {

    /**
     * Class constructor
     */
    public PrintFieldAscendingVenue() {
        Help.addCmd("print_field_ascending_venue", " - print all of Venue sorted in ascending order.");
    }

    /**
     * Prints Venue elements sorted in ascending order.
     * 
     * @return true on success and false if not.
     */
    public boolean execute(SocketChannel sChannel, Object arg) {
        CollectionManager.values().stream()
                .map(Ticket::getVenue)
                .sorted(Venue::compareTo)
                .forEach(a -> AnswerManager.addQueue(sChannel, a.toString()));
        return true;
    }
}
