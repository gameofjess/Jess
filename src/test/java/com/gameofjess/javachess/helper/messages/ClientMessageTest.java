package com.gameofjess.javachess.helper.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.*;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ClientMessageTest {

    /**
     * Test the ClientMessage(String, Date, MessageType) constructor.
     */
    @ParameterizedTest(name = "#{index} - Constructor test with message {0} and date {1}")
    @MethodSource({"getRandomStringsAndDates"})
    void constructTest(String message, Date time) {
        ClientMessage cmsg = new ClientMessage(message, time, MessageType.CHATMESSAGE);
        assertEquals(message, cmsg.getMessage());
        assertEquals(time, cmsg.getTime());
    }

    /**
     * Test the ClientMessage(String) constructor.
     */
    @ParameterizedTest(name = "#{index} - JSON-Constructor test with message {0} and date {1}")
    @MethodSource({"getRandomStringsAndDates"})
    void jsonConstructTest(String message, Date time) {
        ClientMessage cmsg = new ClientMessage(message, time, MessageType.CHATMESSAGE);
        ClientMessage jsonMSG = new ClientMessage(cmsg.toJSON());

        assertEquals(cmsg, jsonMSG);
    }

    /**
     * Test if IllegalArgumentException is thrown when the constructor is given a server-only
     * MessageType.
     */
    @Test
    void invalidArgumentTest() {
        assertThrows(IllegalArgumentException.class, () -> new ClientMessage("", MessageType.SERVERERROR));
        assertThrows(IllegalArgumentException.class, () -> new ClientMessage("", MessageType.SERVERINFO));
        assertThrows(IllegalArgumentException.class, () -> new ClientMessage("", MessageType.BEGINMATCH));
    }

    /**
     * @return random Strings and Dates needed for construct tests
     */
    private static Stream<Arguments> getRandomStringsAndDates() {
        List<String> stringList = List.of("LlcEfo2JG4iqffBnae0p", "86UB9ybOxuRZUACYIBOx", "OsZddZwHxW0E11XViZyJ", "GMpRp5V3UCT7eDKhszd3", "MB7ER25vIii957d3hajk",
                "VrR6qYPLvZZbCbQy5vET", "1M6Nia59pPsk95F3BJkw", "g9vCydyp4BvJdChrruEl", "n4RMDQk1P74R7DEjWEei", "qCw7loLBulDlVVrbAGBR", "W9FAyneMlw8S50QZRE5r",
                "zhnEYSYn58Azxxso70Xi", "qTouHRLWwTcMnWmfepmJ", "FPUrFxujdoke3mjOCHrP", "QwMNY4RZhjCyyHz8jEW0", "scXaPZn8v8l9TAAAgX3I", "BGXziSyG1z458B5YfDfA",
                "mE7EegYopycyO3YBp4jX", "Z2HqRu550K3qCXjbUvdU", "vm0eps2nTqX5Ya7b50O1", "blYMcB4zXzEXS9IffWAu", "TiM85qLFEcUMic1jktLt", "dnEVeqilnZMCK9FgWkjZ",
                "1ptDRU69ZA3hXzZaWjZy", "gcePt3zmHyjBTubXh3Q9", "y4R6Rn0e0pc8lhyqAPvD", "6Yo8spxcOXUtV6xmpXl7", "XzoFSrEmSPkK9yGAqWOA", "JB5cywgASliVYh4jypju",
                "DiJHuYd7mOb4LOA3mJuS", "Sw8kGAGW8HMRZaJtH0pf", "n1wmfA2Jjdn2plnHg6Zw", "hjJJZhW0bbEkCAbZmMH8", "DvrANDZlkQMnr8mlRvQl", "YhP55FO2MmotgTdofHI5",
                "Oi4ba73PozCK0N0kNA7L", "ji9dMbngorUfqn0g3RKE", "j1jGbP9AQFT7HlabfSuS", "UF20yv7JHDSsAs7WVFJM", "cYgvrXQQIxYAUBjGRXAS", "usgEjOy7BdviM04FK6uP",
                "xYHRK1Dferxp5bRCpVUs", "shM0cukvSYIc9dBJG26C", "idYIN1XOW66JnVpFmbHn", "bLbC4h4hsJxnIB7Mt819", "I9BjUMvq8NuMkgTpgFcN", "3Jtgmb24InRCdAnkeIVK",
                "K6otYnGoGOHIUfWUD6Gc", "QDLURah1kPyizFP6m82O", "YpEFG1m9vuw2faAgTvqK", "mOoFlNGsBBgop8O", "zRMKpUn0SjFFSyq", "IhmG2m46y5p4jbc", "OZTbbawMgFyiphN",
                "xoVz68jCXl2xUQV", "7Cy7gTrLuFl1wEV", "aLMJNdLpl997rZO", "jhSlT4CF2nixufC", "IW75TZXVH8DrUA0", "Zj7z3ixw5Sd4DH0", "a9Hpz", "Q5fv9", "JrtBb", "fzoOK", "i558Q",
                "FFHGk", "BX9QU", "UjB4w", "9x2ZW", "");
        Calendar c = new GregorianCalendar();
        List<Date> dateList = new ArrayList<>();
        c.set(9072, Calendar.MAY, 13, 7, 9);
        dateList.add(c.getTime());
        c.set(5585, Calendar.SEPTEMBER, 6, 5, 32);
        dateList.add(c.getTime());
        c.set(440, Calendar.AUGUST, 22, 22, 20);
        dateList.add(c.getTime());
        c.set(6692, Calendar.SEPTEMBER, 23, 24, 58);
        dateList.add(c.getTime());
        c.set(9903, Calendar.NOVEMBER, 1, 22, 44);
        dateList.add(c.getTime());
        c.set(6433, Calendar.FEBRUARY, 4, 7, 18);
        dateList.add(c.getTime());
        c.set(6458, Calendar.FEBRUARY, 28, 18, 21);
        dateList.add(c.getTime());
        c.set(3669, Calendar.JULY, 16, 10, 37);
        dateList.add(c.getTime());
        c.set(4153, Calendar.MARCH, 27, 9, 59);
        dateList.add(c.getTime());
        c.set(8327, Calendar.AUGUST, 21, 22, 47);
        dateList.add(c.getTime());
        c.set(7711, Calendar.SEPTEMBER, 2, 23, 30);
        dateList.add(c.getTime());
        c.set(8808, Calendar.AUGUST, 22, 18, 36);
        dateList.add(c.getTime());
        c.set(9660, Calendar.NOVEMBER, 12, 9, 4);
        dateList.add(c.getTime());
        c.set(6266, Calendar.OCTOBER, 3, 12, 38);
        dateList.add(c.getTime());
        c.set(1058, Calendar.JULY, 20, 11, 19);
        dateList.add(c.getTime());
        c.set(5619, Calendar.JUNE, 1, 10, 48);
        dateList.add(c.getTime());
        c.set(7839, Calendar.MAY, 20, 21, 32);
        dateList.add(c.getTime());
        c.set(9266, Calendar.APRIL, 15, 10, 3);
        dateList.add(c.getTime());
        c.set(3498, Calendar.SEPTEMBER, 25, 24, 40);
        dateList.add(c.getTime());
        c.set(5668, Calendar.SEPTEMBER, 19, 10, 1);
        dateList.add(c.getTime());
        c.set(5871, Calendar.MARCH, 21, 1, 42);
        dateList.add(c.getTime());
        c.set(8568, Calendar.JULY, 23, 7, 22);
        dateList.add(c.getTime());
        c.set(7233, Calendar.APRIL, 19, 3, 48);
        dateList.add(c.getTime());
        c.set(8883, Calendar.JUNE, 7, 11, 7);
        dateList.add(c.getTime());
        c.set(657, Calendar.NOVEMBER, 27, 1, 49);
        dateList.add(c.getTime());
        c.set(4844, Calendar.MARCH, 26, 2, 39);
        dateList.add(c.getTime());
        c.set(8945, Calendar.OCTOBER, 15, 3, 41);
        dateList.add(c.getTime());
        c.set(6149, Calendar.MARCH, 15, 23, 21);
        dateList.add(c.getTime());
        c.set(5017, Calendar.OCTOBER, 13, 9, 1);
        dateList.add(c.getTime());
        c.set(486, Calendar.DECEMBER, 25, 19, 25);
        dateList.add(c.getTime());
        c.set(5756, Calendar.NOVEMBER, 28, 16, 23);
        dateList.add(c.getTime());
        c.set(5729, Calendar.NOVEMBER, 9, 3, 0);
        dateList.add(c.getTime());
        c.set(135, Calendar.JULY, 22, 16, 58);
        dateList.add(c.getTime());
        c.set(8931, Calendar.NOVEMBER, 25, 0, 6);
        dateList.add(c.getTime());
        c.set(7419, Calendar.JULY, 11, 21, 45);
        dateList.add(c.getTime());
        c.set(8012, Calendar.DECEMBER, 5, 22, 26);
        dateList.add(c.getTime());
        c.set(3084, Calendar.NOVEMBER, 21, 20, 12);
        dateList.add(c.getTime());
        c.set(6338, Calendar.FEBRUARY, 26, 15, 40);
        dateList.add(c.getTime());
        c.set(3333, Calendar.APRIL, 20, 5, 2);
        dateList.add(c.getTime());
        c.set(4683, Calendar.MAY, 4, 16, 16);
        dateList.add(c.getTime());
        c.set(2510, Calendar.NOVEMBER, 5, 23, 41);
        dateList.add(c.getTime());
        c.set(2473, Calendar.NOVEMBER, 10, 5, 54);
        dateList.add(c.getTime());
        c.set(6404, Calendar.FEBRUARY, 11, 10, 45);
        dateList.add(c.getTime());
        c.set(3931, Calendar.MAY, 7, 14, 40);
        dateList.add(c.getTime());
        c.set(9336, Calendar.DECEMBER, 5, 9, 33);
        dateList.add(c.getTime());
        c.set(2453, Calendar.OCTOBER, 16, 15, 12);
        dateList.add(c.getTime());
        c.set(5617, Calendar.MARCH, 17, 13, 56);
        dateList.add(c.getTime());
        c.set(2582, Calendar.DECEMBER, 10, 0, 34);
        dateList.add(c.getTime());
        c.set(4920, Calendar.AUGUST, 15, 6, 10);
        dateList.add(c.getTime());
        c.set(6161, Calendar.JUNE, 10, 20, 31);
        dateList.add(c.getTime());

        List<Arguments> argList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            argList.add(Arguments.of(stringList.get(i), dateList.get(i)));
        }

        return argList.stream();
    }

}
