package ru.hoty.utils;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleWorker {
    Scanner scan;

    public ConsoleWorker(Scanner scan) {
        this.scan = scan;
    }

    public String checkConsoleInput() {
        try {
            if (System.in.available() != 0) {
                return scan.next();
            }
        } catch (IOException ignored) {}
        return null;
    }
}
