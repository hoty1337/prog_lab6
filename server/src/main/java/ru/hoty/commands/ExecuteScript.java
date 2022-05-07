package ru.hoty.commands;

import java.nio.channels.SocketChannel;
import ru.hoty.utils.AnswerManager;

/**
 * Class for executing some script from file.
 */
public class ExecuteScript implements CommandInterface {

    /**
     * Class constructor.
     */
    public ExecuteScript() {
        Help.addCmd("execute_script [String]", " - executes some script from file.");
    }

    /**
     * Gets the path to the script and then execute it.
     * 
     * @return true on success and false if not.
     */
    public boolean execute(SocketChannel sChannel, Object arg) {
        AnswerManager.addQueue(sChannel, "-executeScript " + arg.toString());
        return true;
    }
}
