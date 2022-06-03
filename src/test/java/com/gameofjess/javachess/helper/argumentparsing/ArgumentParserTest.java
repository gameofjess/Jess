package com.gameofjess.javachess.helper.argumentparsing;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.gameofjess.javachess.helper.exceptions.InvalidHostnameException;
import com.gameofjess.javachess.helper.exceptions.InvalidOptionException;
import com.gameofjess.javachess.helper.exceptions.InvalidPortException;

public class ArgumentParserTest {

    /**
     * Tests for regular operation with all possible arguments
     *
     * @see ArgumentParser#getOpts(String[])
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
     * @see ArgumentParser#getOpts(String[])
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
     * @see ArgumentParser#getOpts(String[])
     */
    @Test
    void regularPortTest()
            throws InvalidOptionException, InvalidHostnameException, InvalidPortException {
        for (int port = 1024; port <= 65535; port++) {
            String[] args = {"-p", String.valueOf(port)};
            Option portOption = Option.port;
            portOption.setValue(String.valueOf(port));

            assertArrayEquals(ArgumentParser.getOpts(args), new Option[] {portOption});
        }
    }

    /**
     * Tests for regular operation with the host argument and random selection of hostnames
     *
     * @see ArgumentParser#getOpts(String[])
     */
    @Test
    void regularHostnameTest()
            throws InvalidOptionException, InvalidHostnameException, InvalidPortException {

        String[] exampleHostnames = {"example.org", "test.example.org", "stilltesting.example.org"};

        for (String host : exampleHostnames) {
            String[] args = {"-H", host};
            Option hostOption = Option.host;
            hostOption.setValue(host);

            assertArrayEquals(ArgumentParser.getOpts(args), new Option[] {hostOption});
        }


    }

    /**
     * Tests for regular operation with the host argument and random selection of valid IPv4 addresses.
     *
     * @see ArgumentParser#getOpts(String[])
     */
    @ParameterizedTest(name = "#{index} - IP Test with valid IPv4 address {0}")
    @MethodSource("validIPv4Addresses")
    void regularIPTest(String hostIP)
            throws InvalidOptionException, InvalidHostnameException, InvalidPortException {

        String[] args = {"-H", hostIP};
        Option hostOption = Option.host;
        hostOption.setValue(hostIP);

        assertArrayEquals(ArgumentParser.getOpts(args), new Option[] {hostOption});
    }

    /**
     * Tests for InvalidHostnameException with the host argument and random selection of invalid IPv4
     * addresses.
     *
     * @see ArgumentParser#getOpts(String[])
     */
    @ParameterizedTest(name = "#{index} - IP Test with invalid IPv4 address {0}")
    @MethodSource("invalidIPv4Addresses")
    void invalidIPTest(String hostIP) {
        assertThrows(InvalidHostnameException.class,
                () -> ArgumentParser.getOpts(new String[] {"-H", hostIP}));
    }

    /**
     * Tests for InvalidOptionException
     *
     * @see ArgumentParser#getOpts(String[])
     */
    @Test
    void invalidOptionExceptionTest() {
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
        String[] args = {"--thisisatest", "--anotherarg", "--invalidarg", "argwithoutdashes"};

        assertThrows(InvalidOptionException.class, () -> ArgumentParser.getOpts(args));

        assertThrows(InvalidOptionException.class, () -> ArgumentParser.getOpts(new String[] {"-p"}));
        assertThrows(InvalidOptionException.class, () -> ArgumentParser.getOpts(new String[] {"-H"}));
    }

    /**
     * Tests for InvalidPortException
     *
     * @see ArgumentParser#getOpts(String[])
     */
    @Test
    void invalidPortExceptionTest() {
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
     * Tests for InvalidHostnameException with hostnames
     *
     * @see ArgumentParser#getOpts(String[])
     */
    @Test
    void invalidHostnameExceptionTest() {
        String[] invalidHostnames = {"-test.example.org", "test with space.example.org",
                "test/with/slashes.example.org", "test.with.dot."};
        for (String host : invalidHostnames) {
            assertThrows(InvalidHostnameException.class,
                    () -> ArgumentParser.getOpts(new String[] {"-H", host}));
        }
    }

    // Methods that provide information for the above tests

    /**
     * @return Stream of valid IPv4 addresses as Strings.
     */
    private static Stream<String> validIPv4Addresses() {
        return Stream.of("253.220.60.156", "208.16.54.7", "6.119.178.164", "53.79.41.43",
                "94.63.155.108", "212.173.243.145", "40.191.33.51", "155.44.240.191",
                "15.23.222.177", "83.190.28.66", "125.106.214.111", "9.194.58.89", "88.227.80.211",
                "53.44.241.14", "71.98.116.251", "153.8.193.181", "207.250.254.215", "28.67.70.188",
                "39.68.230.84", "235.96.247.37", "15.68.242.41", "143.179.109.245", "106.0.88.195",
                "211.246.240.74", "201.210.62.55", "31.153.214.46", "125.28.114.233",
                "117.100.226.230", "107.22.108.224", "108.183.16.2", "112.243.132.249",
                "4.153.58.133", "17.7.27.173", "70.122.172.98", "14.197.85.210", "248.49.164.58",
                "205.184.195.213", "251.73.100.172", "85.249.39.33", "236.180.233.209",
                "73.39.97.159", "133.173.7.173", "108.224.87.1", "15.154.20.181", "36.37.96.175",
                "60.104.17.177", "78.251.137.164", "247.166.142.216", "196.102.223.117",
                "88.20.124.169", "86.206.21.55", "88.81.41.57", "151.60.73.20", "216.198.101.207",
                "167.17.52.183", "248.3.151.200", "243.125.105.32", "209.49.62.166",
                "107.140.85.191", "109.83.227.69", "225.152.133.5", "199.168.229.213",
                "83.15.185.1", "127.88.69.185", "171.225.175.176", "203.187.70.198",
                "120.253.58.78", "57.54.91.63", "138.108.226.57", "242.57.113.52", "76.219.171.138",
                "179.8.141.246", "44.164.250.32", "116.251.175.141", "86.198.92.185",
                "176.97.184.156", "190.43.215.182", "190.96.131.196", "30.249.22.216",
                "65.86.134.162", "182.85.43.174", "211.170.53.122", "197.34.1.82", "185.250.162.59",
                "220.175.16.248", "68.172.48.155", "233.51.28.0", "116.71.183.8", "142.227.87.24",
                "156.164.242.227", "154.43.21.206", "113.192.181.34", "207.97.109.102",
                "235.178.237.238", "76.144.37.217", "101.225.15.62", "228.225.39.82",
                "32.11.77.230", "148.165.36.209", "121.172.150.217", "215.38.217.168",
                "143.192.152.35", "4.135.205.188", "129.157.66.146", "158.154.169.180",
                "79.71.56.89", "244.164.139.118", "146.16.198.78", "195.16.47.235",
                "135.90.165.244", "193.138.131.173", "66.30.26.193", "45.51.112.118",
                "12.238.25.147", "26.255.188.254", "186.5.180.227", "30.146.34.142",
                "242.109.178.164", "12.75.230.139", "234.254.118.207", "167.186.138.144",
                "35.120.176.30", "192.150.192.95", "173.15.104.233", "154.212.235.249",
                "97.57.37.125", "199.196.145.246", "165.45.193.74", "210.11.208.157",
                "205.166.229.134", "44.74.236.153", "30.111.226.126", "101.75.107.255",
                "5.183.39.179", "162.116.38.146", "129.174.244.167", "147.43.171.86",
                "88.73.146.39", "216.118.68.133", "28.39.180.42", "131.112.239.18", "92.142.188.55",
                "96.76.32.137", "39.101.35.88", "144.28.196.136", "213.250.223.116",
                "145.212.249.174", "1.130.127.63", "148.239.41.143", "179.108.45.247",
                "228.189.51.137", "42.23.63.49", "26.101.59.107", "153.33.154.15", "42.54.148.126",
                "152.217.187.120", "47.81.72.91", "218.40.56.97", "5.79.234.183", "249.42.184.166",
                "207.92.17.80", "2.145.229.89", "138.197.245.211", "206.237.165.127",
                "66.79.34.117", "51.126.201.70", "219.16.143.176", "74.107.150.200", "7.219.29.191",
                "105.190.220.60", "191.148.2.95", "57.131.26.225", "203.237.21.171", "19.219.74.44",
                "93.66.158.187", "113.181.4.225", "177.195.104.204", "175.64.74.147",
                "118.162.63.17", "79.169.14.162", "113.152.235.245", "176.112.219.122",
                "40.212.68.134", "59.56.107.14", "53.120.191.153", "99.240.72.58",
                "122.159.139.155", "235.7.82.166", "53.163.94.234", "252.131.184.199",
                "75.10.135.209", "98.119.232.74", "126.166.224.251", "246.37.64.87",
                "233.233.117.45", "175.118.91.70", "43.134.194.199", "254.49.136.151",
                "111.43.104.251", "68.150.255.208", "85.108.118.123", "7.24.92.37", "119.109.5.2",
                "139.116.187.76", "150.125.161.84", "115.223.107.119", "137.3.157.43",
                "103.250.34.236", "139.10.196.200", "153.234.53.3", "104.27.49.0", "73.142.119.130",
                "98.108.47.30", "214.51.91.163", "67.72.236.165", "145.89.75.82", "184.41.121.146",
                "208.205.32.148", "111.135.113.142", "51.120.247.48", "124.249.41.108",
                "193.50.31.169", "143.21.96.181", "160.195.83.37", "123.194.216.74",
                "236.79.221.157", "21.217.79.217", "251.113.115.193", "250.164.140.145",
                "32.55.18.37", "93.113.196.112", "46.200.131.245", "22.231.173.218",
                "235.202.128.111", "159.69.204.22", "120.62.142.84", "212.142.208.27",
                "129.140.202.236", "149.197.191.41", "174.134.214.69", "162.69.185.137",
                "8.27.202.165", "51.193.161.219", "56.245.23.163", "235.230.35.43",
                "197.157.72.132", "110.17.14.169", "105.98.226.76", "168.248.93.58",
                "103.100.188.122", "226.207.214.8", "55.120.37.31", "96.148.168.159",
                "155.36.168.234", "9.30.115.68", "11.188.44.253", "101.133.118.251",
                "227.113.215.205", "9.65.127.56", "75.64.182.145", "187.52.146.82",
                "187.254.157.254", "107.188.226.231", "172.241.108.220", "207.71.84.138",
                "210.84.211.183", "59.40.144.185", "13.182.173.36", "46.90.48.193",
                "108.244.198.243", "221.219.147.217", "246.169.205.154", "9.218.250.80",
                "63.0.136.85", "139.126.193.79", "8.12.72.29", "195.135.26.184", "42.146.105.237",
                "200.129.75.209", "149.190.131.175", "213.27.17.141", "22.227.102.103",
                "236.14.168.8", "135.201.226.180", "39.1.165.130", "69.252.102.130",
                "211.146.234.163", "200.23.53.121", "80.185.167.4", "75.225.174.107",
                "124.44.110.234", "34.58.232.140", "250.103.226.51", "157.195.73.226",
                "69.131.124.165", "163.175.154.16", "192.168.59.200", "21.119.143.4",
                "56.221.168.181", "161.208.55.43", "210.136.113.166", "132.181.102.24",
                "227.144.143.134", "141.235.15.130", "230.164.236.11", "23.143.244.239",
                "43.199.115.104", "186.237.18.226", "88.43.170.132", "233.135.199.46",
                "78.23.10.37", "238.140.224.231", "99.176.33.65", "235.83.129.64", "231.12.118.235",
                "156.4.201.145", "41.75.110.239", "82.185.185.121", "164.117.25.84",
                "12.177.102.59", "148.79.255.27", "39.165.178.102", "36.48.23.180", "3.41.93.179",
                "5.215.255.197", "91.115.47.216", "43.7.81.170", "189.228.29.210", "187.108.26.97",
                "41.254.8.129", "10.122.74.139", "173.251.31.178", "234.155.184.143",
                "121.55.136.151", "85.247.47.88", "45.109.120.92", "138.53.168.253", "137.98.87.44",
                "125.56.112.244", "90.165.211.227", "92.81.160.237", "3.223.247.129", "30.42.62.23",
                "217.213.210.119", "166.72.156.25", "15.246.181.155", "250.97.88.148",
                "251.198.43.148", "204.110.236.29", "156.40.96.53", "161.139.128.111",
                "167.232.151.146", "128.165.252.73", "15.102.245.23", "106.53.47.152",
                "232.226.27.135", "207.29.55.22", "164.7.206.14", "0.15.249.204", "82.99.84.32",
                "61.126.31.23", "216.160.168.234", "29.110.66.11", "144.174.55.141",
                "119.196.243.128", "44.27.116.232", "244.144.77.7", "63.15.192.248",
                "205.125.68.115", "13.64.214.38", "124.225.51.108", "99.181.238.94",
                "112.49.246.116", "252.222.42.25", "83.2.85.53", "35.0.80.210", "110.115.13.66",
                "245.251.74.181", "235.36.208.47", "211.228.124.133", "193.207.161.95",
                "40.1.254.37", "88.55.240.160", "77.39.9.111", "130.132.98.44", "140.119.245.71",
                "255.27.4.253", "22.234.131.208", "155.138.38.220", "108.134.119.74",
                "109.252.137.124", "96.34.128.145", "177.239.211.146", "37.237.94.217",
                "224.197.235.243", "79.206.12.87", "118.3.207.33", "75.36.230.94", "101.110.156.61",
                "71.151.148.240", "67.24.58.0", "131.59.155.200", "65.43.89.162", "219.171.27.0",
                "107.97.54.70", "16.209.29.212", "99.146.194.227", "107.30.132.217",
                "150.153.152.44", "158.77.55.128", "177.226.58.126", "210.137.178.195",
                "223.193.146.79", "108.61.42.99", "35.103.236.88", "234.94.106.17",
                "112.176.146.77", "141.141.66.42", "21.38.255.55", "111.174.220.6", "245.225.99.79",
                "135.82.170.63", "186.83.137.21", "145.164.139.221", "31.160.110.209",
                "160.215.191.128", "23.185.113.23", "182.157.46.158", "33.223.238.117",
                "220.68.185.86", "116.197.163.44", "227.96.125.142", "196.239.46.252",
                "38.129.132.120", "222.208.18.201", "244.9.31.33", "123.181.130.236",
                "248.242.154.3", "21.88.170.47", "16.20.163.138", "3.30.147.133", "57.99.36.237",
                "242.142.42.83", "98.131.152.3", "137.150.247.206", "39.131.142.102",
                "136.124.37.154", "77.119.114.129", "128.12.1.106", "245.229.108.213",
                "185.164.29.17", "44.115.54.44", "74.199.1.175", "71.37.9.225", "20.80.89.190",
                "186.75.19.166", "69.250.161.132", "230.235.127.6", "171.255.75.71", "6.165.145.63",
                "8.29.72.41", "161.90.241.184", "132.234.223.165", "2.246.247.27",
                "168.140.129.143", "53.76.124.183", "55.81.172.87", "22.9.87.175", "231.69.45.146",
                "232.191.159.250", "13.190.81.243", "33.88.210.225", "15.184.65.203",
                "94.160.9.133", "48.239.57.87", "145.130.32.202", "22.165.61.1", "248.96.4.170",
                "100.115.176.129", "160.122.138.42", "250.96.37.169", "77.253.152.51",
                "96.243.19.64", "52.254.79.119", "178.178.86.125", "42.204.239.82",
                "182.226.21.133", "81.247.73.137", "89.6.211.45", "185.186.11.76", "197.18.46.76",
                "179.168.218.203", "199.53.186.119", "55.101.47.200", "190.222.226.109",
                "113.85.125.54", "124.35.97.200", "120.86.181.101", "24.31.85.251",
                "81.184.218.230", "60.249.62.107", "0.2.89.57", "139.196.40.245", "23.180.166.250",
                "227.224.245.19", "72.1.48.181", "203.184.222.8", "168.219.126.240",
                "241.137.107.62", "193.47.237.36", "49.209.26.31", "0.159.251.172", "194.6.151.43",
                "105.14.22.167", "64.191.99.155", "6.76.255.138", "18.204.116.107", "73.45.61.170",
                "132.68.34.174", "112.85.97.53", "147.210.90.9", "191.176.165.195",
                "40.152.213.172", "75.11.139.246", "238.104.54.96", "13.98.11.65", "15.108.44.43",
                "92.242.91.32", "120.64.64.85", "138.100.57.73", "181.215.151.25", "245.203.215.4",
                "175.151.56.133", "56.11.227.232", "197.220.203.134", "162.129.57.234",
                "31.161.169.73", "210.169.145.54", "82.146.32.229", "9.113.171.158",
                "38.203.58.162", "46.88.136.254", "146.185.126.87", "228.255.232.122",
                "91.111.250.41", "183.254.155.4", "214.217.18.154", "145.31.198.111",
                "143.74.14.119", "197.121.107.114", "108.81.177.87", "237.126.213.189",
                "170.252.149.84", "223.33.99.100", "201.72.128.140", "182.59.47.54",
                "53.207.166.216", "220.88.80.45", "114.153.28.53", "143.77.107.253",
                "33.235.146.26", "146.228.20.34", "63.208.21.167", "26.197.32.35", "209.68.83.133",
                "6.87.94.92", "105.81.15.98", "179.226.113.185", "122.67.127.188", "228.123.183.0",
                "137.187.173.210", "34.248.22.40", "241.147.119.59", "228.128.186.11",
                "157.130.35.193", "187.218.116.181", "62.236.52.52", "36.250.53.73", "140.7.51.119",
                "200.196.215.249", "1.1.129.57", "56.176.84.149", "9.181.225.121", "43.48.204.46",
                "221.66.248.167", "154.93.156.164", "235.4.99.66", "34.226.229.236",
                "87.186.39.183", "47.229.203.238", "190.121.32.81", "166.177.251.3",
                "131.160.104.200", "99.80.20.195", "198.207.138.41", "131.121.30.11",
                "168.216.28.107", "16.184.3.1", "178.37.158.103", "33.247.211.182", "0.191.112.210",
                "6.132.212.25", "143.48.13.123", "93.138.120.107", "8.229.138.203",
                "230.166.198.171", "74.151.177.107", "181.214.104.53", "39.125.112.237",
                "135.52.62.182", "9.72.24.248", "79.241.198.245", "233.217.130.100",
                "17.14.113.117", "24.99.84.34", "62.155.20.125", "24.127.45.155", "24.53.199.1",
                "174.181.66.109", "24.236.32.159", "34.132.61.150", "184.175.64.250",
                "99.112.91.72", "53.222.90.99", "116.210.41.241", "239.38.25.95", "97.120.72.214",
                "160.46.165.172", "171.184.143.148", "209.99.52.58", "216.214.12.206",
                "39.157.115.85", "246.215.45.199", "24.54.30.56", "237.74.149.121",
                "179.65.245.224", "139.9.126.176", "232.92.21.202", "23.80.6.132", "25.189.22.213",
                "59.169.181.73", "60.243.92.67", "89.124.157.153", "60.179.32.96",
                "150.208.250.171", "231.20.209.8", "231.150.100.185", "78.141.47.109",
                "192.133.179.81", "57.64.191.4", "85.243.131.174", "152.58.195.172",
                "151.114.215.186", "85.192.17.219", "146.168.234.26", "71.220.191.62",
                "246.26.129.155", "13.149.161.153", "102.234.79.114", "249.160.221.78",
                "216.42.198.244", "55.113.166.242", "209.189.80.178", "33.183.101.148",
                "185.189.61.213", "94.36.137.34", "3.139.40.250", "23.65.202.204", "146.11.49.190",
                "246.239.173.20", "139.119.203.116", "30.66.156.183", "202.153.204.29",
                "80.76.184.127", "149.115.107.140", "5.157.192.190", "119.120.93.20",
                "14.202.181.231", "42.39.212.94", "13.252.185.20", "151.5.194.209",
                "15.205.108.100", "208.162.27.221", "110.90.246.144", "140.149.90.112",
                "102.169.247.42", "145.223.223.116", "158.81.36.159", "146.124.230.224",
                "68.67.25.169", "191.196.163.240", "39.45.114.237", "161.151.197.58",
                "196.65.104.152", "0.194.2.61", "241.119.5.26", "14.175.236.217", "175.213.132.239",
                "15.82.247.234", "5.77.201.12", "34.238.217.129", "165.37.57.140", "67.135.31.211",
                "101.176.182.133", "11.207.181.214", "100.219.31.214", "179.77.121.249",
                "234.249.224.150", "52.76.33.202", "131.61.121.60", "113.0.111.252",
                "153.45.222.189", "36.54.168.61", "243.164.112.154", "186.87.7.52", "83.19.100.198",
                "66.185.145.6", "145.157.144.70", "210.239.174.177", "37.56.137.39",
                "182.130.173.127", "14.150.107.168", "153.186.165.184", "215.8.60.17",
                "26.69.245.71", "76.18.178.105", "150.51.229.244", "135.110.186.90", "9.27.126.24",
                "100.95.101.13", "103.153.187.48", "138.116.150.198", "153.197.79.88",
                "178.184.79.243", "211.19.122.25", "39.207.87.69", "8.213.11.89", "63.88.15.218",
                "13.24.125.187", "203.76.125.240", "210.250.32.171", "18.156.137.144",
                "236.156.79.55", "69.104.153.11", "195.223.232.122", "0.148.68.238",
                "11.102.133.110", "127.134.129.26", "188.146.156.191", "187.0.74.87",
                "155.209.212.252", "172.223.163.144", "151.76.126.12", "49.43.77.235",
                "174.183.29.186", "134.177.127.27", "249.137.0.61", "2.88.51.163", "233.88.163.196",
                "197.140.181.251", "239.17.255.114", "44.169.113.57", "6.176.90.199",
                "44.48.248.220", "137.38.14.49", "14.222.168.8", "94.77.120.147", "84.119.229.2",
                "50.154.32.181", "99.85.175.75", "27.11.13.112", "27.157.135.25", "123.11.132.41",
                "235.224.157.119", "26.188.232.215", "237.112.102.232", "124.27.210.2",
                "17.254.13.102", "150.105.229.255", "239.31.91.51", "89.43.96.53", "162.164.37.205",
                "111.192.60.35", "171.180.55.179", "77.139.232.34", "56.240.100.206",
                "218.224.56.244", "121.100.1.67", "26.46.85.196", "244.207.225.171",
                "190.88.249.102", "240.162.126.187", "157.232.175.55", "82.18.3.185",
                "48.38.103.164", "160.110.165.50", "32.104.21.18", "42.16.13.225",
                "179.144.100.242", "120.36.117.87", "92.136.234.141", "187.80.190.2",
                "37.78.210.179", "103.181.212.230", "28.5.1.210", "8.228.111.97", "242.65.196.4",
                "86.119.3.246", "103.56.138.124", "6.30.89.231", "96.220.186.38", "102.217.215.73",
                "42.89.52.81", "109.151.93.237", "97.223.223.166", "253.194.242.182",
                "208.49.181.9", "118.230.72.150", "52.134.217.88", "137.102.86.172",
                "200.238.176.185", "222.87.65.110", "113.230.84.166", "253.24.111.114",
                "63.61.50.213", "9.27.50.82", "81.61.130.126", "128.95.93.149", "239.36.218.222",
                "169.63.109.235", "45.128.135.142", "125.15.132.39", "117.99.19.179",
                "213.162.226.179", "194.143.11.117", "6.12.153.12", "133.243.79.214",
                "10.173.73.117", "170.41.179.169", "53.62.244.29", "104.43.176.7", "32.131.233.59",
                "178.187.198.76", "143.126.106.195", "183.183.214.15", "137.130.167.162",
                "217.171.53.30", "160.200.122.178", "58.248.170.246", "209.139.61.186",
                "5.39.18.188", "82.76.255.68", "237.237.121.237", "63.118.128.229", "89.205.55.240",
                "142.114.195.190", "58.33.98.94", "85.223.246.167", "73.16.18.8", "168.98.70.210",
                "105.51.76.76", "17.225.15.142", "225.128.77.38", "132.247.124.216",
                "255.76.171.106", "14.182.42.154", "65.199.55.28", "233.65.247.132",
                "91.176.255.223", "49.28.142.196", "197.49.163.233", "189.51.85.25", "1.253.226.26",
                "174.15.48.134", "60.117.164.137", "206.110.106.54", "76.55.236.53",
                "7.128.100.121", "208.248.188.205", "137.133.105.253", "63.158.58.197",
                "184.253.102.136", "38.133.88.237", "32.137.177.204", "131.40.15.174",
                "137.176.160.162", "78.126.212.84", "19.233.22.193", "186.161.49.57",
                "8.105.240.78", "179.91.102.251", "34.34.80.130", "239.96.236.129", "12.41.37.27",
                "224.85.235.132", "130.219.123.15", "231.91.143.134", "237.112.226.217",
                "200.178.250.132", "108.60.57.120", "104.45.214.109", "47.66.96.69",
                "242.27.75.196", "159.226.178.134", "144.39.192.122", "175.128.33.211",
                "230.29.120.106", "75.101.214.199", "185.235.74.126", "211.77.50.3",
                "88.225.170.121", "144.196.225.251", "247.90.42.70", "102.162.213.37",
                "154.101.130.210", "133.222.202.33", "127.29.237.97", "228.199.171.154",
                "38.253.208.129", "179.210.78.237", "76.231.73.48", "27.243.101.20",
                "118.169.223.182", "221.241.44.79", "77.36.16.49", "166.82.191.56", "111.218.61.92",
                "47.140.8.204", "112.64.35.7", "215.102.93.217", "226.138.60.167", "248.37.213.3",
                "192.45.202.210", "65.200.113.147", "142.180.236.84", "33.246.39.29",
                "167.41.131.132", "246.252.243.177", "149.98.224.64", "124.151.24.103",
                "200.172.191.8", "122.189.206.236", "24.78.108.133", "22.180.138.203",
                "64.172.19.85", "69.60.157.107", "83.242.178.141", "90.61.149.249", "85.22.98.253",
                "247.160.35.44", "33.172.237.250", "65.148.130.180", "30.20.255.232",
                "63.163.127.168", "43.23.253.244", "190.159.251.172", "153.117.212.205",
                "211.61.43.221", "82.170.231.204", "49.151.251.60", "220.61.106.38", "9.91.17.126",
                "41.56.121.66", "225.149.249.187", "216.3.58.178", "217.161.69.158", "90.40.40.6",
                "110.135.167.38", "14.46.8.27", "42.70.135.82", "43.48.105.66", "172.133.220.244",
                "38.121.57.153", "4.57.74.162", "25.184.27.71", "71.133.111.107", "90.152.197.93",
                "73.221.9.203", "159.66.150.215", "52.235.112.99", "64.129.78.154", "88.142.50.43",
                "45.175.134.206", "6.101.8.113", "129.228.42.217", "83.153.227.139",
                "187.110.155.165", "28.210.222.131", "146.219.230.20", "53.176.226.216",
                "254.41.190.193", "55.99.162.145", "88.135.33.128", "233.42.182.28",
                "209.225.111.87", "243.152.202.180", "204.125.232.235", "115.155.134.144",
                "61.136.218.225", "252.92.217.102");
    }

    /**
     * @return Stream of invalid IPv4 addresses as Strings.
     */
    private static Stream<String> invalidIPv4Addresses() {
        return Stream.of("000.000.000.000", "00.00.00.00", "1.2.3.04", "1.02.03.4", "1.2", "1.2.3",
                "1.2.3.4.5", "192.168.1.1.1", "256.1.1.1", "1.256.1.1", "1.1.256.1", "1.1.1.256",
                "-100.1.1.1", "1.-100.1.1", "1.1.-100.1", "1.1.1.-100", "1...1", "1..1", "1.1.1.1.",
                "");
    }

}
