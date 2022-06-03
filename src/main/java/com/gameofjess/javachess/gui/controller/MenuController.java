package com.gameofjess.javachess.gui.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gameofjess.javachess.client.ConnectionHandler;
import com.gameofjess.javachess.helper.exceptions.InvalidHostnameException;
import com.gameofjess.javachess.helper.exceptions.InvalidPortException;
import com.gameofjess.javachess.server.Server;
import com.gameofjess.javachess.server.ServerBuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MenuController extends Controller {

    private static final Logger log = LogManager.getLogger(MenuController.class);

    private ConnectionHandler connectionHandler;

    @FXML
    private Text ipAddressText;
    @FXML
    private TextField address;
    @FXML
    private TextField username;

    public void initialize() {
        if (ipAddressText != null) {
            try {
                String localIPAddress = InetAddress.getLocalHost().getHostAddress();
                String remoteIPAddress;

                URL url = new URL("https://ipaddr.gameofjess.com");
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    remoteIPAddress = br.readLine();
                } else {
                    remoteIPAddress = "Could not establish connection to server!";
                }


                ipAddressText.setText("Your local IP Address: " + localIPAddress + "\n" + "Your remote IP Address: " + remoteIPAddress);
            } catch (UnknownHostException ex) {
                ipAddressText.setStyle("-fx-text-fill: #550000");
                ipAddressText.setText("Could not determine your IP-Address!");
            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Gets user inputs and calls the connect method.
     *
     * @param event ActionEvent
     * @throws InvalidHostnameException If the hostname is invalid.
     * @throws InvalidPortException If the port is invalid.
     * @throws URISyntaxException If the URI is invalid.
     */
    public void joinGame(ActionEvent event) throws InvalidHostnameException, InvalidPortException, URISyntaxException {
        // TODO spilt port from IP Adress
        // TODO: start connection handler in thread
        connect(address.getText(), 8887, username.getText());
        log.debug("Joining game");
    }

    /**
     * Connects to the server.
     *
     * @param address IP address of the server.
     * @param port Port of the server.
     * @param username Username of the client.
     * @throws InvalidHostnameException If the hostname is invalid.
     * @throws InvalidPortException If the port is invalid.
     * @throws URISyntaxException If the URI is invalid.
     */
    public boolean connect(String address, int port, String username) throws InvalidHostnameException, InvalidPortException, URISyntaxException {
        ConnectionHandler connectionHandler = new ConnectionHandler(address, port);
        log.debug("Trying to connect to " + address + "using Port " + port + "as " + username);
        return connectionHandler.connect(username);
    }

    /**
     * Starts the server and calls the connect function to connect to the server.
     *
     * @param event ActionEvent
     * @throws InvalidPortException If the port is invalid.
     */
    public void hostGame(ActionEvent event) throws InvalidHostnameException, InvalidPortException, URISyntaxException, IOException {
        String host = "127.0.0.1";
        int port = 8887;

        ServerBuilder serverBuilder = new ServerBuilder();
        serverBuilder.setHost(host);
        serverBuilder.setPort(port);

        Server server = serverBuilder.build();
        Thread serverThread = new Thread(server);
        serverThread.start();

        // Connect to created Server and switch to game scene
        if (connect(host, port, username.getText())) {
            switchGameScene(event);
        }
    }

}
