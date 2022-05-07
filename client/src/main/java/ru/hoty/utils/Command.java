package ru.hoty.utils;

import java.io.Serializable;

public class Command implements Serializable{
    private final String cmd;
    private final Object arg;

    public Command(String cmd, Object arg) {
        this.cmd = cmd;
        this.arg = arg;
    }

    public String getCommand() {
        return cmd;
    }

    public Object getArgument() {
        return arg;
    }
}
