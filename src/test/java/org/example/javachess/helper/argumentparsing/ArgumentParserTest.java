package org.example.javachess.helper.argumentparsing;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.example.javachess.helper.exceptions.InvalidHostnameException;
import org.example.javachess.helper.exceptions.InvalidOptionException;
import org.example.javachess.helper.exceptions.InvalidPortException;
import org.junit.jupiter.api.Test;

public class ArgumentParserTest {

    /**
     * Tests for regular operation with all possible arguments
     * 
     * @see org.example.javachess.helper.argumentparsing.ArgumentParser#getOpts(String[])
     */
    @Test
    void regularTest()
            throws InvalidOptionException, InvalidHostnameException, InvalidPortException {
        String[] args = {"-s", "-H", "10.1.1.1", "-p", "8888"};
        Option hostname = Option.host;
        Option port = Option.port;
        hostname.setValue("10.1.1.1");
        port.setValue("8888");

        assertArrayEquals(ArgumentParser.getOpts(args),
                new Option[] {Option.dedicatedServer, hostname, port});
    }

    /**
     * Tests for regular operation with the server argument
     * 
     * @see org.example.javachess.helper.argumentparsing.ArgumentParser#getOpts(String[])
     */
    @Test
    void regularServerTest()
            throws InvalidOptionException, InvalidHostnameException, InvalidPortException {
        String[] args = {"-s"};
        assertArrayEquals(ArgumentParser.getOpts(args), new Option[] {Option.dedicatedServer});

        args = new String[] {"--server"};
        assertArrayEquals(ArgumentParser.getOpts(args), new Option[] {Option.dedicatedServer});
    }

    /**
     * Tests for regular operation with the port argument
     * 
     * @see org.example.javachess.helper.argumentparsing.ArgumentParser#getOpts(String[])
     */
    @Test
    void regularPortTest()
            throws InvalidOptionException, InvalidHostnameException, InvalidPortException {
        for (int port = 1000; port <= 65535; port++) {
            String[] args = {"-p", String.valueOf(port)};
            Option portOption = Option.port;
            portOption.setValue(String.valueOf(port));

            assertArrayEquals(ArgumentParser.getOpts(args), new Option[] {portOption});
        }
    }

    /**
     * Tests for regular operation with the host argument
     * 
     * @see org.example.javachess.helper.argumentparsing.ArgumentParser#getOpts(String[])
     */
    @Test
    void regularHostTest()
            throws InvalidOptionException, InvalidHostnameException, InvalidPortException {
        // -- IP Test --
        // Can be set depending on if a thorough test should be done...
        boolean fast = true;

        if (fast) {
            for (int thirdOctet = 1; thirdOctet <= 255; thirdOctet++) {
                for (int fourthOctet = 1; fourthOctet <= 255; fourthOctet++) {

                    String hostIP = 172 + "." + 16 + "." + thirdOctet + "." + fourthOctet;

                    String[] args = {"-H", hostIP};
                    Option hostOption = Option.host;
                    hostOption.setValue(hostIP);

                    assertArrayEquals(ArgumentParser.getOpts(args), new Option[] {hostOption});
                }
            }
        } else {
            for (int firstOctet = 1; firstOctet <= 255; firstOctet++) {
                for (int secondOctet = 1; secondOctet <= 255; secondOctet++) {
                    for (int thirdOctet = 1; thirdOctet <= 255; thirdOctet++) {
                        for (int fourthOctet = 1; fourthOctet <= 255; fourthOctet++) {

                            String hostIP = firstOctet + "." + secondOctet + "." + thirdOctet + "."
                                    + fourthOctet;

                            String[] args = {"-H", hostIP};
                            Option hostOption = Option.host;
                            hostOption.setValue(hostIP);

                            assertArrayEquals(ArgumentParser.getOpts(args),
                                    new Option[] {hostOption});
                        }
                    }
                }
            }
        }

        // -- Hostname Test --

        String[] exampleHostnames = {"example.org", "test.example.org", "stilltesting.example.org"};

        for (String host : exampleHostnames) {
            String[] args = {"-H", host};
            Option hostOption = Option.host;
            hostOption.setValue(host);

            assertArrayEquals(ArgumentParser.getOpts(args), new Option[] {hostOption});
        }


    }

    /**
     * Tests for InvalidOptionException
     * 
     * @see org.example.javachess.helper.argumentparsing.ArgumentParser#getOpts(String[])
     */
    @Test
    void InvalidOptionExceptionTest() {
        List<Character> shortAliases = new ArrayList<>();

        for (Option o : Option.values()) {
            shortAliases.add(o.getShortAlias());
        }

        // -- A-Z --
        for (char c = 65; c <= 90; c++) {
            if (!(shortAliases.contains(c))) {
                char finalC = c;
                assertThrows(InvalidOptionException.class,
                        () -> ArgumentParser.getOpts(new String[] {"-" + finalC}));
            }
        }

        // -- a-z --
        for (char c = 97; c <= 122; c++) {
            if (!(shortAliases.contains(c))) {
                char finalC = c;
                assertThrows(InvalidOptionException.class,
                        () -> ArgumentParser.getOpts(new String[] {"-" + finalC}));
            }
        }

        // String Test
        String[] args = {"--thisisatest", "--anotherarg", "--invalidarg"};

        assertThrows(InvalidOptionException.class, () -> ArgumentParser.getOpts(args));
    }

    /**
     * Tests for InvalidPortException
     * 
     * @see org.example.javachess.helper.argumentparsing.ArgumentParser#getOpts(String[])
     */
    @Test
    void InvalidPortExceptionTest() {
        // Below 1000

        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            assertThrows(InvalidPortException.class,
                    () -> ArgumentParser.getOpts(new String[] {"-p", String.valueOf(finalI)}));
        }

        // Above 65535

        for (int i = 65536; i < 100000; i++) {
            int finalI = i;
            assertThrows(InvalidPortException.class,
                    () -> ArgumentParser.getOpts(new String[] {"-p", String.valueOf(finalI)}));
        }

        // Something completely different

        assertThrows(InvalidPortException.class,
                () -> ArgumentParser.getOpts(new String[] {"-p", "somethingdifferent"}));
    }

    /**
     * Tests for InvalidHostnameException
     * 
     * @see org.example.javachess.helper.argumentparsing.ArgumentParser#getOpts(String[])
     */
    @Test
    void invalidHostnameExceptionTest() {
        String[] invalidIPs = {"1234.1.1.1", "1.1234.1.1", "test.1.1.1"};
        for (String ip : invalidIPs) {
            assertThrows(InvalidHostnameException.class,
                    () -> ArgumentParser.getOpts(new String[] {"-H", ip}));
        }

        String[] invalidHostnames = {"-test.example.org", "test with space.example.org",
                "test/with/slashes.example.org", "test.with.dot."};
        for (String host : invalidHostnames) {
            assertThrows(InvalidHostnameException.class,
                    () -> ArgumentParser.getOpts(new String[] {"-H", host}));
        }
    }



}
