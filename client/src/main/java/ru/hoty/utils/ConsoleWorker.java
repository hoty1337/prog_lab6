package ru.hoty.utils;

import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Class for working with input
 */
public class ConsoleWorker {
    private static Scanner firstScanner = null;
    private static Scanner scanner = null;

    /**
     * Sets a new input for the program
     * 
     * @param tScanner  - Scanner input stream
     */
    public static void setScanner(Scanner tScanner) {
        if(firstScanner == null) {
            firstScanner = tScanner;
        }
        scanner = tScanner;
    }

    /**
     * Gets a input of the program
     * 
     * @return  - Scanner input stream
     */
    public static Scanner getScanner() {
        return scanner;
    }

    public static Scanner getFirstScanner() {
        return firstScanner;
    }
    
    /**
     * Reads a line from the set scanner
     * 
     * @return  - Input string
     */
    public static String readLine() {
        String data = null;
        try {
            if(!scanner.hasNextLine()) {
                return null;
            }
            data = scanner.nextLine().replaceAll("\\p{C}", "?").trim();
            if(data.length() > 100) {
                System.out.println(TextFormatter.toYellow("Нельзя вводить более 100 символов в одной команде."));
                return "";
            }
        } catch (NoSuchElementException e) {
            System.out.println(TextFormatter.toRed("Выход из программы..."));
            return null;
        }
        if(getScanner() != getFirstScanner()) {
            System.out.println(data);
        }
        return data;
    }
}
