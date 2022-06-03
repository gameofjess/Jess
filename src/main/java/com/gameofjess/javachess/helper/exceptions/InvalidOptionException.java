package org.example.javachess.helper.exceptions;

/**
 * Invalid argument / option
 */
public class InvalidOptionException extends Exception {
    public InvalidOptionException() {
        super("An invalid Option was given!");
    }
}
