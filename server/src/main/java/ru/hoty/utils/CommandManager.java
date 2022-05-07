package ru.hoty.utils;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class CommandManager {
    private static Map<SocketChannel, Queue<Command>> map = new HashMap<>();

    public static void addQueue(SocketChannel sChannel, Command cmd) {
        if(!map.containsKey(sChannel)) {
            map.put(sChannel, new LinkedList<>());
        }
        map.get(sChannel).add(cmd);
    }

    public static Command getCommand(SocketChannel sChannel) {
        return (map.get(sChannel) == null ? null : map.get(sChannel).poll());
    }

    public static boolean isEmpty(SocketChannel sChannel) {
        return (map.get(sChannel) == null || map.get(sChannel).isEmpty());
    }
}
