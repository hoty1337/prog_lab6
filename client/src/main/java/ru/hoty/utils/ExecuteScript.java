package ru.hoty.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class ExecuteScript {
    private SocketWorker sWorker;
    private Scanner oldScanner;
    private List<String> runningScripts;

    /**
     * Class constructor.
     */
    public ExecuteScript(SocketWorker sWorker, Scanner tScanner, List<String> runningScripts) {
        this.sWorker = sWorker;
        this.oldScanner = tScanner;
        this.runningScripts = runningScripts;
    }

    /**
     * Gets the path to the script and then execute it.
     * 
     * @return true on success and false if not.
     */
    public boolean execute(String file) {
        if(file == null) {
            if(!ConsoleWorker.getScanner().hasNext()) {
                return false;
            }
            System.out.println("Введите путь к скрипту:");
            file = ConsoleWorker.readLine();
        }
        if(runningScripts.contains(file)) {
            System.out.println(TextFormatter.toYellow("Обнаружена рекурсия, пропускаем команду..."));
            return false;
        }
        File fFile = new File(file);
        try {
            ConsoleWorker.setScanner(new Scanner(fFile));
        } catch (FileNotFoundException e) {
            System.err.println(TextFormatter.toRed("Файл (" + file + ") не найден!"));
            return false;
        }
        if(!fFile.canRead()) {
            System.err.println(TextFormatter.toRed("Файл (" + file + ") не имеет доступа к чтению!"));
            return false;
        }
        if(!fFile.canWrite()) {
            System.out.println(TextFormatter.toYellow("Файл (" + file + ") открыт в режиме чтения!"));
        }
        runningScripts.add(file);
        String cmd = null;
        while((cmd = ConsoleWorker.readLine()) != null) {
            if(cmd.contains("execute_script")) {
                new ExecuteScript(sWorker, ConsoleWorker.getScanner(), runningScripts).execute(cmd.split(" ").length > 1 ? cmd.split(" ")[1] : null);
            } else {
                CommandWorker.xCommand(sWorker, cmd.split(" "));
            }
		}
        ConsoleWorker.setScanner(oldScanner);
        return true;
    }
}
