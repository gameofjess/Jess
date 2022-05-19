package org.example.javachess.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerCommandListener implements Runnable {

    private static final Logger log = LogManager.getLogger(ServerCommandListener.class);

    private final Server server;
    private final InputStream stream;

    /**
     * Constructor ServerCommandListener
     * 
     * @param server Server that commands are sent to.
     * @param stream InputStream the commandlistener shall listen on.
     */
    public ServerCommandListener(Server server, InputStream stream) {
        this.server = server;
        this.stream = stream;
    }

    /**
     * Run-method that continually scans for input
     */
    public void run() {
        BufferedReader stdinReader = new BufferedReader(new InputStreamReader(stream));
        String inputLine;

        try {
            while((inputLine = stdinReader.readLine()) != null){
                parseCommand(inputLine);
            }
        } catch(IOException ex){
            log.error(ex.getMessage());
        }
    }

    /**
     * Parses a given command and executes methods accordingly-
     * 
     * @param cmd Command
     */

    public void parseCommand(String cmd) {
        switch (cmd) {
            case "exit" -> {
                log.info("Exiting...");
                System.exit(1);
            }
            case "stop" -> {
                try {
                    log.info("Server is preparing to stop!");
                    server.stop(20);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
            case "list" -> {
                String users = "";
                Stream<String> serverUserStream = Stream.of(server.getUsers()).sorted();
                String[] serverUsers = serverUserStream.toArray(String[]::new);
                for (int i = 0; i < serverUsers.length; i++) {
                    String s = serverUsers[i];
                    if(i == serverUsers.length - 1){
                        users = users.concat(s);
                    } else {
                        users = users.concat(s + ", ");
                    }
                }
                log.info("The following users are connected: " + users);
            }
            default -> log.info("Unknown command: " + cmd);
        }
    }

}
