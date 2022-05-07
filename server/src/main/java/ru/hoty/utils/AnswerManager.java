package ru.hoty.utils;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class AnswerManager {
    private static final Map<SocketChannel, Queue<Answer>> map = new HashMap<>();
    public static void addQueue(SocketChannel sChannel, String str) {
        if(!map.containsKey(sChannel)) {
            map.put(sChannel, new LinkedList<>());
        }
        addQueue(sChannel, new Answer(str));
    }

    public static void addQueue(SocketChannel sChannel, Answer ans) {
        if(!map.containsKey(sChannel)) {
            map.put(sChannel, new LinkedList<>());
        }
        map.get(sChannel).add(ans);
    }

    public static String getString(SocketChannel sChannel) {
        Answer ans = map.get(sChannel).poll();
        return (ans == null ? null : ans.getAnswer());
    }

}
