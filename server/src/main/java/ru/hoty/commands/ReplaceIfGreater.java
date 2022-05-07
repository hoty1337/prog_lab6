package ru.hoty.commands;

import java.nio.channels.SocketChannel;

import ru.hoty.collection.CollectionManager;
import ru.hoty.collection.Ticket;
import ru.hoty.utils.AnswerManager;
import ru.hoty.utils.TextFormatter;

/**
 * Class for replacing elements if new is greater than old.
 */
public class ReplaceIfGreater implements CommandInterface {

    /**
     * Class constructor
     */
    public ReplaceIfGreater() {
        Help.addCmd("replace_if_greater [Long]", " - replaces element if new is greater than old.");
    }

    /**
     * Gets all the necessary data of new element, then replace if new is greater than old.
     * 
     * @return true on success and false if not.
     */
    public boolean execute(SocketChannel sChannel, Object arg) {
        if(!(arg instanceof Ticket)) {
            AnswerManager.addQueue(sChannel, "-createObject replace_if_greater");
            return false;
        }
        Ticket ticket = (Ticket) arg;
        
        try {
            if(CollectionManager.get(ticket.getId()).getPrice() < ticket.getPrice()) {
                CollectionManager.replace(ticket.getId(), ticket);
            } else {
                AnswerManager.addQueue(sChannel, TextFormatter.toRed("Price of the old element was higher, not replaced."));
                return false;
            }
        } catch(Exception e) {
            e.printStackTrace();
            AnswerManager.addQueue(sChannel, TextFormatter.toRed("Something went wrong... Try your request again."));
            return false;
        }
        AnswerManager.addQueue(sChannel, TextFormatter.toGreen("Element with ID " + ticket.getId() + " was successfully updated."));
        return true;
    }
}
