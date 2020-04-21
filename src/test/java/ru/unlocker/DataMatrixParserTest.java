package ru.unlocker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataMatrixParserTest {

    @Test
    void shouldParseSampleOfficialDoc() {
        String input = "010460043993125621JgXJ5.T\u001d8005112000\u001d930001\u001d923zbrLA==\u001d24014276281";
        String result = DataMatrixParser.dataMatrixToAtol(input);
        assertEquals("44 4d 04 2f 1f 96 81 78 4a 67 58 4a 35 2e 54 31 31 32 30 30 30", result);
    }

    @Test
    void shouldParseSampleOne() {
        String input = "010290000059848521QRYY3*4FN;rAO\u001d918039\u001d92NEbFr8pJB4HOTcbrBsKy40qJQ/Fs4pxMFn80IKUFJLBwGAiPp5nv+YCBww110fO3YZJH3uWFs8eumIczN1HTbQ==";
        String result = DataMatrixParser.dataMatrixToAtol(input);
        assertEquals("44 4d 02 a3 35 81 69 d5 51 52 59 59 33 2a 34 46 4e 3b 72 41 4f", result);
    }

    @Test
    void shouldParseSampleTwo() {
        String input = "010290000059848521>TTFR9*ZZ5fq,\u001d918039\u001d92NT/IA0uY+DJJzU54ItCs0O2FcTx+p4xhILI6hwUanf0oC02FcpAIbe05t7qPn6lsxWZ7njM2oz5fsNYDpqKryA==";
        String result = DataMatrixParser.dataMatrixToAtol(input);
        assertEquals("44 4d 02 a3 35 81 69 d5 3e 54 54 46 52 39 2a 5a 5a 35 66 71 2c", result);
    }

    @Test
    void shouldParseSampleThree() {
        String input = "010468006512802221)x/JtGI+LE'_u\u001d918039\u001d92B5noxP7+HNYAriVWpSc9ssHHo++6nKuO8RVnQuMruP36M2vehrb1jJfKWer/9ahvoazC3PfPaHC+3kfB+0xU7g==";
        String result = DataMatrixParser.dataMatrixToAtol(input);
        assertEquals("44 4d 04 41 a9 9e 96 56 29 78 2f 4a 74 47 49 2b 4c 45 27 5f 75", result);
    }



}