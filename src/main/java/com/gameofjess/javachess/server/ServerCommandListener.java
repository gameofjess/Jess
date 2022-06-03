package org.example.javachess.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerCommandListener implements Runnable {

    private static final Logger log = LogManager.getLogger(ServerCommandListener.class);

    private Server server;
    private final InputStream stream;

    volatile boolean stop = false;

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
                if (stop) {
                    Thread.currentThread().interrupt();
                    log.debug("Returning due to boolean stop set to true!");
                    return;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Parses a given command and executes methods accordingly.
     * 
     * @param cmd Command
     */

    public void parseCommand(String cmd) {
        switch (cmd) {
            case "start" -> start();
            case "stop" -> {
                stop();
                log.info("Continue with 'exit' to exit or with 'start' to start again");
            }
            case "restart" -> restart();
            case "exit" -> exit();
            case "list" -> list();
            default -> log.info("Unknown command: " + cmd);
        }
    }

    private void start(){
        if (server.getServerStatus()) {
            log.error("Server is already started!");
            return;
        }

        int port = server.getPort();
        String host = server.getAddress().getHostName();

        ServerBuilder sb = new ServerBuilder();
        log.debug("Setting server port to {} and host to {}", port, host);
        sb.setPort(port);
        sb.setHost(host);

        server = sb.build();

        log.info("Starting server...");
        server.start();
    }

    private void stop(){
        log.info("Server is preparing to stop!");

        boolean isClosed = false;

        long start = System.currentTimeMillis();

        try {
            server.stop(20);
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss,SSSS");
        log.debug("Starting stop procedure at {}.", simpleDateFormat.format(new Date(start)));

        while (System.currentTimeMillis() - start < 1000) {
            if (!server.getServerStatus()) {
                isClosed = true;
                break;
            }
        }

        long end = System.currentTimeMillis();

        if(isClosed){
            log.info("Server successfully closed within {} ms!", end-start);
            log.debug("Exact closing time: " + simpleDateFormat.format(new Date(end)));
        } else {
            throw new RuntimeException("Could not stop server in time!");
        }
    }

    private void restart(){
        stop();
        start();
    }

    private void exit() {
        if(!server.getServerStatus()){
            log.info("Exiting...");
            stop = true;
        } else {
            log.info("Server is not stopped yet. Proceeding to stop, then exit.");
            stop();

            boolean isClosed = false;
            long start =  System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 1000) {
                if (!server.getServerStatus()) {
                    isClosed = true;
                    break;
                }
            }

            if(!isClosed){
                log.error("Could not stop server. Proceeding to exit unsafely");
            }

            stop = true;
        }
    }

    private void list(){
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
}
