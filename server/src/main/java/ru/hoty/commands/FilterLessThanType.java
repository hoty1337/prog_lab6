package ru.hoty.commands;

import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.stream.Collectors;

import ru.hoty.collection.CollectionManager;
import ru.hoty.collection.Ticket;
import ru.hoty.collection.TicketType;
import ru.hoty.utils.AnswerManager;
import ru.hoty.utils.TextFormatter;

/** 
 * Class for printing all elements which type less than type of this.
 */
public class FilterLessThanType implements CommandInterface {

    /**
     * Class constructor
     * 
     * @param cManager  - Class to work with org.hoty.collection.
     */
    public FilterLessThanType() {
        Help.addCmd("filter_less_than_type [TicketType]", " - prints all elements which type less than type of this.");
    }

    /**
     * Gets the ID of element, then prints all elements which type less than type of this.
     * 
     * @return true on success and false if not.
     */
    public boolean execute(SocketChannel sChannel, Object arg) {
        String el;
        //AnswerManager.addQueue(sChannel, "Введите TicketType, элементы меньше которого будут выведены:");
        el = (String) arg;
        if(el == null) {
            AnswerManager.addQueue(sChannel, TextFormatter.toYellow("You should enter one of these types: VIP, USUAL, BUDGETARY, CHEAP"));
            return false;
        }
        try {
            final TicketType tType = TicketType.valueOf(el);
            List<Ticket> ticket = CollectionManager.values().stream().filter(a -> a.getType().compareTo(tType) < 0).collect(Collectors.toList());
            if(ticket.size() == 0) {
                AnswerManager.addQueue(sChannel, TextFormatter.toYellow("Elements lower than " + el + " not found."));
            } else {
                for (Ticket t : ticket) {
                    AnswerManager.addQueue(sChannel, t.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            AnswerManager.addQueue(sChannel, TextFormatter.toRed("Type should be one of these types: VIP, USUAL, BUDGETARY, CHEAP!"));
            return false;
        }

        return true;
    }
}
