package ru.hoty;

import java.util.Scanner;

import ru.hoty.utils.CommandWorker;
import ru.hoty.utils.ConsoleWorker;
import ru.hoty.utils.SocketWorker;

public class Main {
    
    public static void main(String[] args) {
        
        SocketWorker sWorker = new SocketWorker("localhost", 1337);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            sWorker.close();
            System.out.println("Всего доброго.");
      }));
        ConsoleWorker.setScanner(new Scanner(System.in));
        String[] cmd;
        String inLine;
        inLine = ConsoleWorker.readLine();
        cmd = (inLine == null ? null : inLine.split(" "));
        while(cmd != null && !cmd[0].equals("") && !cmd[0].equals("exit")) {
            sWorker.connect();
            if(!CommandWorker.xCommand(sWorker, cmd)) {
                continue;
            }
            inLine = ConsoleWorker.readLine();
            cmd = (inLine == null ? null : inLine.split(" "));
        }
        sWorker.close();
    }
}
