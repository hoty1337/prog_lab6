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
     * 
     * @param cManager  - Class to work with org.hoty.collection.
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
        Set<Float> setPrices = new HashSet<>();
        for (Ticket t : CollectionManager.values()) {
            setPrices.add(t.getPrice());
        }
        for (Float price : setPrices) {
            AnswerManager.addQueue(sChannel, price.toString());
        }
        return true;
    }
}
