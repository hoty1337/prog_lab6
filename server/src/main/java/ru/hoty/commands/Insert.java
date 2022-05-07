package ru.hoty.commands;

import java.nio.channels.SocketChannel;

import ru.hoty.collection.CollectionManager;
import ru.hoty.collection.Ticket;
import ru.hoty.utils.AnswerManager;
import ru.hoty.utils.TextFormatter;

/**
 * Class for adding a new element into org.hoty.collection.
 */

public class Insert implements CommandInterface {
    /**
     * Class constructor
     */
    public Insert() {
        Help.addCmd("insert", " - adding a new element into org.hoty.collection.");
    }

    /**
     * Gets the necessary new org.hoty.collection items and then add them.
     * 
     * @return true on success and false if not.
     */
    public boolean execute(SocketChannel sChannel, Object arg) {
        if(!(arg instanceof Ticket)) {
            AnswerManager.addQueue(sChannel, "-createObject insert");
            return false;
        }
		Ticket ticket = (Ticket) arg;
        try {
            CollectionManager.put(ticket.getId(), ticket);
        } catch(Exception e) {
            e.printStackTrace();
            AnswerManager.addQueue(sChannel, TextFormatter.toRed("Something went wrong... Try your request again."));
            return false;
        }
        AnswerManager.addQueue(sChannel, TextFormatter.toGreen("Element with ID " + ticket.getId() + " has been successfully added."));
        return true;
    }
}
