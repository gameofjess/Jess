package com.gameofjess.javachess.helper.argumentparsing;

import java.util.Arrays;
import java.util.Optional;

/**
 * Represents all possible command line options for JavaChess.
 */

public enum Option {

    /**
     * Just the dedicated server should be started.
     */
    DEDICATED_SERVER('s', "server"),
    /**
     * A different host should be used.
     */
    HOST('H', "host"),
    /**
     * A different port shall be used.
     */
    PORT('p', "port");

    private final char shortAlias;
    private final String longAlias;
    private String value;

    Option(Character shortAlias, String longAlias) {
        this.shortAlias = shortAlias;
        this.longAlias = longAlias;
    }

    /**
     * Setter for an options value. E.g. "--port 1234"
     * 
     * @param value value to be set.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Getter for an options value.
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * Getter for shortAlias
     * 
     * @return the short alias for the specific option object.
     */
    public char getShortAlias() {
        return this.shortAlias;
    }

    /**
     * Getter for longAlias
     * 
     * @return the long alias for the specific option object.
     */
    public String getLongAlias() {
        return this.longAlias;
    }

    /**
     * This method can be used to get a specific Option object by its short alias.
     * 
     * @param shortAlias short alias of object to be returned.
     * @return Option object that is represented by given short alias or null if such an object does
     *         not exist.
     */

    public static Option getByShortAlias(char shortAlias) {
        Optional<Option> optionalOption = Arrays.stream(values()).parallel().filter(option -> option.getShortAlias() == shortAlias).findAny();
        return optionalOption.orElse(null);
    }

    /**
     * This method can be used to get a specific Option object by its long alias.
     * 
     * @param longAlias long alias of object to be returned.
     * @return Option object that is represented by given long alias or null if such an object does
     *         not exist.
     */

    public static Option getByLongAlias(String longAlias) {
        Optional<Option> optionalOption = Arrays.stream(values()).parallel().filter(option -> option.getLongAlias().equals(longAlias)).findAny();
        return optionalOption.orElse(null);
    }

}
