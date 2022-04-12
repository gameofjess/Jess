package org.example.javachess.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Scanner;

public class ServerCommandListener implements Runnable{

    private static final Logger log = LogManager.getLogger(ServerCommandListener.class);

    private final Server server;
    private final InputStream stream;

    /**
     * Constructor ServerCommandListener
     * @param server Server that commands are sent to.
     * @param stream InputStream the commandlistener shall listen on.
     */
    public ServerCommandListener(Server server, InputStream stream){
        this.server = server;
        this.stream = stream;
    }

    /**
     * Run-method that continually scans for input
     */
    public void run(){
        Scanner scanner = new Scanner(stream);
        while(true){
            if(scanner.hasNext()){
                parseCommand(scanner.nextLine());
            }
        }
    }

    /**
     * Parses a given command and executes methods accordingly-
     * @param cmd Command
     */

    public void parseCommand(String cmd) {
        switch(cmd){
            case "stop":
                try {
                    log.info("Server is preparing to stop!");
                    server.stop(20);
                    System.exit(1);
                } catch (InterruptedException e){
                    log.error(e.getMessage());
                }
                break;
            default:
                log.info("Unknown command: " + cmd);
                break;
        }
    }

}
