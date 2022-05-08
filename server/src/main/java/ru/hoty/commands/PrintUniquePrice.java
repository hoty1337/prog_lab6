package ru.hoty.commands;

import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Set;
import ru.hoty.collection.CollectionManager;
import ru.hoty.collection.Ticket;
import ru.hoty.utils.AnswerManager;

/**
 * Class for printing all unique prices.
 */
public class PrintUniquePrice implements CommandInterface {

    /**
     * Class constructor
     */
    public PrintUniquePrice() {
        Help.addCmd("print_unique_price", " - prints all unique prices.");
    }

    /**
     * Prints all unique prices in org.hoty.collection.
     * 
     * @return true on success and false if not.
     */
    public boolean execute(SocketChannel sChannel, Object arg) {
        CollectionManager.values().stream()
                .map(Ticket::getPrice)
                .distinct()
                .forEachOrdered(a -> AnswerManager.addQueue(sChannel, a.toString()));

        return true;
    }
}
