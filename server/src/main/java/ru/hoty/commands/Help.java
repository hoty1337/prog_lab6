package ru.hoty.commands;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import ru.hoty.utils.AnswerManager;
import ru.hoty.utils.TextFormatter;

/**
 * Class for printing a list of org.hoty.commands and their descriptions.
 */

public class Help implements CommandInterface {
    private static Map<String, String> listCmds;

    public Help() {
        listCmds = new HashMap<>();
        listCmds.put("help", " - returns a decription of all org.hoty.commands.");
    }

    /**
     * Print list of org.hoty.commands in the console.
     * 
     * @return true on success and false if not.
     */
    public boolean execute(SocketChannel sChannel, Object arg) {
        for (String t : listCmds.keySet()) {
            AnswerManager.addQueue(sChannel, t + TextFormatter.toPurple(listCmds.get(t)));
        }
        return true;
    }

    /**
     * Adding the command to the help list.
	 * 
     * @param cmd	- Name of command
     * @param desc	- Description of command
     */
    public static void addCmd(String cmd, String desc) {
        listCmds.put(cmd, desc);
    }
}
