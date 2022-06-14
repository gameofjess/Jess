package com.gameofjess.javachess.helper.argumentparsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


public class OptionTest {

    /**
     * Tests the getValue and setValue methods of the Option enum.
     * 
     * @see Option
     */
    @ParameterizedTest(name = "#{index} - Option Value Test with String {0}")
    @MethodSource("getRandomStrings")
    void valueTest(String randomString){
        Option.HOST.setValue(randomString);
        assertEquals(randomString, Option.HOST.getValue());
    }

    /**
     * Tests the getByShortAlias method of the Option enum.
     * 
     * @see Option
     */
    @Test
    void getByShortAliasTest(){
        assertEquals(Option.DEDICATED_SERVER, Option.getByShortAlias('s'));
        assertEquals(Option.HOST, Option.getByShortAlias('H'));
        assertEquals(Option.PORT, Option.getByShortAlias('p'));
    }

    /**
     * Tests the getByLongAlias method of the Option enum.
     * 
     * @see Option
     */
    @Test
    void getByLongAliasTest(){
        assertEquals(Option.DEDICATED_SERVER, Option.getByLongAlias("server"));
        assertEquals(Option.HOST, Option.getByLongAlias("host"));
        assertEquals(Option.PORT, Option.getByLongAlias("port"));
    }

    /**
     * @return random Strings needed for valueTest
     */
    private static Stream<String> getRandomStrings(){
        return Stream.of(
                "LlcEfo2JG4iqffBnae0p",
                "86UB9ybOxuRZUACYIBOx",
                "OsZddZwHxW0E11XViZyJ",
                "GMpRp5V3UCT7eDKhszd3",
                "MB7ER25vIii957d3hajk",
                "VrR6qYPLvZZbCbQy5vET",
                "1M6Nia59pPsk95F3BJkw",
                "g9vCydyp4BvJdChrruEl",
                "n4RMDQk1P74R7DEjWEei",
                "qCw7loLBulDlVVrbAGBR",
                "W9FAyneMlw8S50QZRE5r",
                "zhnEYSYn58Azxxso70Xi",
                "qTouHRLWwTcMnWmfepmJ",
                "FPUrFxujdoke3mjOCHrP",
                "QwMNY4RZhjCyyHz8jEW0",
                "scXaPZn8v8l9TAAAgX3I",
                "BGXziSyG1z458B5YfDfA",
                "mE7EegYopycyO3YBp4jX",
                "Z2HqRu550K3qCXjbUvdU",
                "vm0eps2nTqX5Ya7b50O1",
                "blYMcB4zXzEXS9IffWAu",
                "TiM85qLFEcUMic1jktLt",
                "dnEVeqilnZMCK9FgWkjZ",
                "1ptDRU69ZA3hXzZaWjZy",
                "gcePt3zmHyjBTubXh3Q9",
                "y4R6Rn0e0pc8lhyqAPvD",
                "6Yo8spxcOXUtV6xmpXl7",
                "XzoFSrEmSPkK9yGAqWOA",
                "JB5cywgASliVYh4jypju",
                "DiJHuYd7mOb4LOA3mJuS",
                "Sw8kGAGW8HMRZaJtH0pf",
                "n1wmfA2Jjdn2plnHg6Zw",
                "hjJJZhW0bbEkCAbZmMH8",
                "DvrANDZlkQMnr8mlRvQl",
                "YhP55FO2MmotgTdofHI5",
                "Oi4ba73PozCK0N0kNA7L",
                "ji9dMbngorUfqn0g3RKE",
                "j1jGbP9AQFT7HlabfSuS",
                "UF20yv7JHDSsAs7WVFJM",
                "cYgvrXQQIxYAUBjGRXAS",
                "usgEjOy7BdviM04FK6uP",
                "xYHRK1Dferxp5bRCpVUs",
                "shM0cukvSYIc9dBJG26C",
                "idYIN1XOW66JnVpFmbHn",
                "bLbC4h4hsJxnIB7Mt819",
                "I9BjUMvq8NuMkgTpgFcN",
                "3Jtgmb24InRCdAnkeIVK",
                "K6otYnGoGOHIUfWUD6Gc",
                "QDLURah1kPyizFP6m82O",
                "YpEFG1m9vuw2faAgTvqK",
                "mOoFlNGsBBgop8O",
                "zRMKpUn0SjFFSyq",
                "IhmG2m46y5p4jbc",
                "OZTbbawMgFyiphN",
                "xoVz68jCXl2xUQV",
                "7Cy7gTrLuFl1wEV",
                "aLMJNdLpl997rZO",
                "jhSlT4CF2nixufC",
                "IW75TZXVH8DrUA0",
                "Zj7z3ixw5Sd4DH0",
                "a9Hpz",
                "Q5fv9",
                "JrtBb",
                "fzoOK",
                "i558Q",
                "FFHGk",
                "BX9QU",
                "UjB4w",
                "9x2ZW",
                "9N7en"
        );
    }

}
