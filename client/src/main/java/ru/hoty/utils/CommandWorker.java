package ru.hoty.utils;

import java.util.LinkedList;

import ru.hoty.collection.Ticket;

public class CommandWorker {
    public static boolean xCommand(SocketWorker sWorker, String[] cmd) {
        if(!sWorker.write(new Command(cmd[0], (cmd.length < 2 ? null : cmd[1])))) {
            return false;
        }
        Answer ans = sWorker.read();
        if(ans == null) {
            return false;
        }
        String[] request = ans.getAnswer().split(" ");
        if(request[0].contains("-createObject")) {
            Ticket ticket = CreateObject.invoke(sWorker, new Ticket(), request[1]);
            if(ticket != null) {
                System.out.println(ticket);
                sWorker.write(new Command(request[1], ticket));
                ans = sWorker.read();
            }
        } else if(request[0].contains("-executeScript")) {
            new ExecuteScript(sWorker, ConsoleWorker.getScanner(), new LinkedList<>()).execute(request.length > 1 ? request[1] : null);
        }
        if(!ans.getAnswer().contains("-createObject") && !ans.getAnswer().contains("-executeScript")) {
            System.out.println(ans.getAnswer());
        }
        return true;
    }
}
