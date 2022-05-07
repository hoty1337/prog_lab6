package ru.hoty.commands;

import java.nio.channels.SocketChannel;

import ru.hoty.collection.CollectionManager;
import ru.hoty.collection.Ticket;
import ru.hoty.utils.AnswerManager;
import ru.hoty.utils.TextFormatter;

/**
 * Class for updating element with given id.
 */
public class Update implements CommandInterface {

    public Update() {
        Help.addCmd("update [Long]", " - updates element with given id.");
    }

    /**
     * Gets the ID and all necessary data of new element to update it.
     */
    public boolean execute(SocketChannel sChannel, Object arg) {
        if(arg == null || !(arg instanceof Ticket)) {
            AnswerManager.addQueue(sChannel, "-createObject update");
            return false;
        }
        Ticket ticket = (Ticket) arg;
        try {
            CollectionManager.replace(ticket.getId(), ticket);
        } catch(Exception e) {
            e.printStackTrace();
            AnswerManager.addQueue(sChannel, TextFormatter.toRed("Something went wrong... Try your request again."));
            return false;
        }
        AnswerManager.addQueue(sChannel, TextFormatter.toGreen("Element with ID " + ticket.getId() + " was successfully updated."));
        return true;
    }
}
