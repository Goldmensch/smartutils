package de.goldmensch.smartutils.plugin.exceptions;

public class NoCommandFoundException extends RuntimeException{

    public NoCommandFoundException(String command) {
        super("no command found for name: " + command + ", have you entered it in the plugin.yml?");
    }

}
