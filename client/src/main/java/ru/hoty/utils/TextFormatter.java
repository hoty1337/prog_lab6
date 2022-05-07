package ru.hoty.utils;

/**
 * Class for formatting text in different colors
 */
public class TextFormatter {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Formats string to red
     * 
     * @param text  - Text to change color
     * @return      - Text with changed color
     */
    public static String toRed(String text) {
        return ANSI_RED + text + ANSI_RESET;
    }

    /**
     * Formats string to green
     * 
     * @param text  - Text to change color
     * @return      - Text with changed color
     */
    public static String toGreen(String text) {
        return ANSI_GREEN + text + ANSI_RESET;
    }

    /**
     * Formats string to yellow
     * 
     * @param text  - Text to change color
     * @return      - Text with changed color
     */
    public static String toYellow(String text) {
        return ANSI_YELLOW + text + ANSI_RESET;
    }

    /**
     * Formats string to blue
     * 
     * @param text  - Text to change color
     * @return      - Text with changed color
     */
    public static String toBlue(String text) {
        return ANSI_BLUE + text + ANSI_RESET;
    }

    /**
     * Formats string to purple
     * 
     * @param text  - Text to change color
     * @return      - Text with changed color
     */
    public static String toPurple(String text) {
        return ANSI_PURPLE + text + ANSI_RESET;
    }

    /**
     * Formats string to cyan
     * 
     * @param text  - Text to change color
     * @return      - Text with changed color
     */
    public static String toCyan(String text) {
        return ANSI_CYAN + text + ANSI_RESET;
    }

    /**
     * Formats string to white
     * 
     * @param text  - Text to change color
     * @return      - Text with changed color
     */
    public static String toWhite(String text) {
        return ANSI_WHITE + text + ANSI_RESET;
    }
}
