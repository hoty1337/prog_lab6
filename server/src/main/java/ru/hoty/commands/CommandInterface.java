package ru.hoty.commands;

import java.nio.channels.SocketChannel;

/**
 * Interface for org.hoty.commands.
 */
public interface CommandInterface {

    /**
     * Method for execute command.
     * 
     * @return true on success and false if not.
     */
    boolean execute(SocketChannel sChannel, Object arg);
}
