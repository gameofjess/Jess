package org.example.javachess.helper.exceptions;

/**
 * Invalid hostname
 */
public class InvalidHostnameException extends Exception {
    public InvalidHostnameException(String hostname) {
        super("Invalid hostname given: " + hostname);
    }
}
