package org.example.javachess.helper.exceptions;

/**
 * Invalid port
 */
public class InvalidPortException extends Exception{
    public InvalidPortException(String port) {
        super("The invalid port " + port + "was given!");
    }
}
