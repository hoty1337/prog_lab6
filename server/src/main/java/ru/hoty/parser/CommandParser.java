package ru.hoty.parser;

import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ru.hoty.commands.*;

/**
 * Class for parsing and executing org.hoty.commands from scanner.
 */
public class CommandParser {
	private static Map<String, CommandInterface> mapCmd;
	private static List<String> historyScripts;

	public static void loadCommands() {
		historyScripts = new LinkedList<>();
		mapCmd = new TreeMap<>();
		mapCmd.put("help", new Help());
		mapCmd.put("info", new Info());
		mapCmd.put("show", new Show());
		mapCmd.put("insert", new Insert());
		mapCmd.put("update", new Update());
		mapCmd.put("remove_key", new RemoveKey());
		mapCmd.put("clear", new Clear());
		mapCmd.put("execute_script", new ExecuteScript());
		mapCmd.put("remove_lower", new RemoveLower());
		mapCmd.put("replace_if_greater", new ReplaceIfGreater());
		mapCmd.put("remove_lower_key", new RemoveLowerKey());
		mapCmd.put("filter_less_than_type", new FilterLessThanType());
		mapCmd.put("print_unique_price", new PrintUniquePrice());
		mapCmd.put("print_field_ascending_venue", new PrintFieldAscendingVenue());
		mapCmd.put("get", new Get());
	}

	public static Map<String, CommandInterface> getMapCmd() {
		return mapCmd;
	}

	/**
	 * Reads the command.
	 */
	public static int executeCmd(SocketChannel sChannel, String cmd, Object arg) {
		try {
			mapCmd.get(cmd).execute(sChannel, arg);
		} catch(NullPointerException ex) {
			return -1;
		}
		clearScriptHistory();
		return 0;
	}

	/**
	 * Gets a string command, then executes it
	 * 
	 * @param cmd	- String command
     * @return true on success and false if not.
	 */
	public static boolean executeCommand(SocketChannel sChannel, String cmd, Object arg) {
		return mapCmd.get(cmd).execute(sChannel, arg);
	}
	
	/**
	 * Gets string path to file, then add it
	 * to history of scripts
	 * 
	 * @param file	- Path to file
	 */
	public static void addScriptToHistory(String file) {
		historyScripts.add(file);
	}

	/**
	 * Gets a string path to file, then remove it
	 * from history of scripts
	 * 
	 * @param file	- Path to file
	 */
	public static void removeScriptFromHistory(String file) {
		historyScripts.remove(file);
	}

	/**
	 * Clears history of scripts
	 */
	public static void clearScriptHistory() {
		historyScripts.clear();
	}

	/**
	 * Returns list of history scripts
	 * 
	 * @return list of history scripts
	 */
	public static List<String> getHistoryScripts() {
		return historyScripts;
	}
}
