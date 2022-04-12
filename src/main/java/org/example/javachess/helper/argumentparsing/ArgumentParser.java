package org.example.javachess.helper.argumentparsing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.javachess.helper.exceptions.InvalidHostnameException;
import org.example.javachess.helper.exceptions.InvalidOptionException;
import org.example.javachess.helper.exceptions.InvalidPortException;

import java.util.ArrayList;
import java.util.List;

public class ArgumentParser {

    private static final Logger log = LogManager.getLogger(ArgumentParser.class);

    /**
     * This method parses the given arguments.
     * @param args arguments to be parsed
     * @return It returns an array of options depending on what arguments have been passed to the method
     * @throws InvalidPortException thrown when the given port is invalid - i.e. outside of the range 1000-65535
     * @throws InvalidOptionException thrown when an unknown argument is passed to the method
     * @throws InvalidHostnameException thrown when an invalid hostname is passed to the method
     */
    public static Option[] getOpts(String[] args) throws InvalidPortException, InvalidOptionException, InvalidHostnameException {
        List<Option> optionList = new ArrayList<>();
        log.debug("Starting argument parsing...");
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (arg.startsWith("-") && arg.length() > 1) {
                Option option;
                if (arg.startsWith("--") && arg.length() > 2) {
                    option = Option.getByLongAlias(arg.substring(2));
                } else {
                    option = Option.getByShortAlias(arg.charAt(1));
                }

                if(option != null){
                    log.debug("Got option " + option.getLongAlias());
                }

                if(option == Option.port){
                    if(!(args.length < i+2)){
                        int port;
                        try{
                            port = Integer.parseInt(args[i+1]);
                        } catch (NumberFormatException e){
                            throw new InvalidPortException(args[i+1]);
                        }
                        log.debug("Successfully parsed potential port " + port);
                        if(port  <= 65535 && port >= 1000){
                            option.setValue(args[i+1]);
                            log.debug("Set port value to " + port);
                        } else {
                            throw new InvalidPortException(args[i+1]);
                        }
                    } else {
                        throw new InvalidOptionException();
                    }
                }

                if (option == Option.host) {
                    if(!(args.length < i+2)){
                        String host = args[i+1];
                        log.debug("Got host-argument " + host);
                        String regexHostname = "^(([a-zA-Z]{1})|([a-zA-Z]{1}[a-zA-Z]{1})|([a-zA-Z]{1}[0-9]{1})|([0-9]{1}[a-zA-Z]{1})|([a-zA-Z0-9][a-zA-Z0-9-_]{1,61}[a-zA-Z0-9]))\\.([a-zA-Z]{2,6}|[a-zA-Z0-9-]{2,30}\\.[a-zA-Z]{2,3})$";
                        String regexIP = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";

                        if(host.matches(regexIP) || host.matches(regexHostname)){
                            option.setValue(args[i+1]);
                            log.debug("Set hostname value to " + host);
                        } else {
                            throw new InvalidHostnameException(host);
                        }
                    } else {
                        throw new InvalidOptionException();
                    }
                }

                optionList.add(option);
            }
        }



        List<Option> finalOptions = optionList.stream().distinct().toList();

        if(finalOptions.contains(null)){
            throw new InvalidOptionException();
        }

        return finalOptions.toArray(new Option[0]);
    }
}
