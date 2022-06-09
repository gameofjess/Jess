package com.gameofjess.javachess.helper.exceptions;

/**
 * Invalid argument / option
 */
public class InvalidOptionException extends Exception {
    public InvalidOptionException() {
        super("An invalid option was given!");
    }
}
