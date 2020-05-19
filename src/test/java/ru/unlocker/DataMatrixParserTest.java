package ru.unlocker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test suite for {@link DataMatrixParser}.
 */
class DataMatrixParserTest {

    @Test
    void shouldParseSampleFirstOfficialDoc() {
        String input = "\u001d010460043993125621JgXJ5.T\u001d8005112000\u001d930001\u001d923zbrLA==\u001d24014276281";
        String result = DataMatrixParser.dataMatrixToAtol(input);
        assertEquals("44 4d 04 2f 1f 96 81 78 4a 67 58 4a 35 2e 54 31 31 32 30 30 30", result);
    }

    @Test
    void shouldParseSampleSecondOfficialDoc() {
        String input = "\u001d010460406000600021N4N57RSCBUZTQ\u001d2403004002910161218\u001d1724010191ffd0\u001d92tIAF/YVoU4roQS3M/m4z78yFq0fc/WsSmLeX5QkF/YVWwy8IMYAeiQ91Xa2z/fFSJcOkb2N+uUUmfr4n0mOX0Q==";
        String result = DataMatrixParser.dataMatrixToAtol(input);
        assertEquals("44 4d 04 2f f7 5c 76 70 4e 34 4e 35 37 52 53 43 42 55 5a 54 51", result);
    }

    @Test
    void shouldParseSampleOne() {
        String input = "\u001d010290000059848521QRYY3*4FN;rAO\u001d918039\u001d92NEbFr8pJB4HOTcbrBsKy40qJQ/Fs4pxMFn80IKUFJLBwGAiPp5nv+YCBww110fO3YZJH3uWFs8eumIczN1HTbQ==";
        String result = DataMatrixParser.dataMatrixToAtol(input);
        assertEquals("44 4d 02 a3 35 81 69 d5 51 52 59 59 33 2a 34 46 4e 3b 72 41 4f", result);
    }

    @Test
    void shouldParseSampleTwo() {
        String input = "\u001d010290000059848521>TTFR9*ZZ5fq,\u001d918039\u001d92NT/IA0uY+DJJzU54ItCs0O2FcTx+p4xhILI6hwUanf0oC02FcpAIbe05t7qPn6lsxWZ7njM2oz5fsNYDpqKryA==";
        String result = DataMatrixParser.dataMatrixToAtol(input);
        assertEquals("44 4d 02 a3 35 81 69 d5 3e 54 54 46 52 39 2a 5a 5a 35 66 71 2c", result);
    }

    @Test
    void shouldParseSampleThree() {
        String input = "\u001d010468006512802221)x/JtGI+LE'_u\u001d918039\u001d92B5noxP7+HNYAriVWpSc9ssHHo++6nKuO8RVnQuMruP36M2vehrb1jJfKWer/9ahvoazC3PfPaHC+3kfB+0xU7g==";
        String result = DataMatrixParser.dataMatrixToAtol(input);
        assertEquals("44 4d 04 41 a9 9e 96 56 29 78 2f 4a 74 47 49 2b 4c 45 27 5f 75", result);
    }

    @Test
    void shouldParseSampleFour_withoutLeadingGS() {
        String input = "010468006509499021h,8T4A8(G5xB)\u001d91003A\u001d92I47sJj5MnQJ98ps+LidUO62yermNTPrkGCHdB8kkTMAau9Tx3l3HWFETbrVdSES4pywJiLymoepfI1dfTKzWtw==";
        String result = DataMatrixParser.dataMatrixToAtol(input);
        assertEquals("44 4d 04 41 a9 9e 15 4e 68 2c 38 54 34 41 38 28 47 35 78 42 29", result);
    }

    @Test
    void shouldParseSampleFive_withoutLeadingGS() {
        String input = "010290000025903421Xt(.U1AJSR%JO\u001d918039\u001d92/zNXU3g2kJdVKtuOQrl5v5bAVZXaonE6iWUNfaVvJumg8kDOOJmVKd2RI198i70/HnkWCEVZ39TmwHNkjQyEoA==";
        String result = DataMatrixParser.dataMatrixToAtol(input);
        assertEquals("44 4d 02 a3 35 7c 3b da 58 74 28 2e 55 31 41 4a 53 52 25 4a 4f", result);
    }

    @Test
    void shouldThrowWhenCodeIsNotSeparated() {
        String input = "010290000025903421Xt(.U1AJSR%JO91803992/zNXU3g2kJdV";
        RuntimeException assertion = Assertions.assertThrows(RuntimeException.class, () -> DataMatrixParser.dataMatrixToAtol(input));
        assertEquals("Must contain at least 2 groups", assertion.getMessage());
    }

    @Test
    void shouldThrowWhenStartGroupNotFound() {
        String input = "92I47sJj5MnQJ98ps+Lid\u001dUO62yermNTPrkGCHdB8kkTMAau\u001dXt(.U1AJSR%JO91803992/zNXU3g2kJdV";
        RuntimeException assertion = Assertions.assertThrows(RuntimeException.class, () -> DataMatrixParser.dataMatrixToAtol(input));
        assertEquals("Start group not found", assertion.getMessage());
    }
}