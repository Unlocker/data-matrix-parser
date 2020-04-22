package ru.unlocker;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataMatrixParser {

    /**
     * A delimiter between different code groups (GS1).
     */
    private static final String GROUP_DELIMITER = "\\u001d";
    /**
     * Pattern to parse a group with a serial number.
     */
    private static final Pattern SERIAL_NUMBER_GROUP_PATTERN = Pattern.compile("^01(\\d+?)21(.+)$");
    /**
     * The length of serial number fragment.
     */
    private static final int SERIAL_NUMBER_LENGTH = 12;
    /**
     * A prefix for a group with measurement unit.
     */
    private static final String MEASUREMENT_GROUP_PREFIX = "8005";

    private DataMatrixParser() {
    }


    /**
     * Parses a data matrix into Atol Online format.
     *
     * @param dataMatrix data matrix content
     * @return Atol encoded string
     */
    public static String dataMatrixToAtol(String dataMatrix) {
        String[] groups = dataMatrix.split(GROUP_DELIMITER);
        if (groups.length < 2) {
            throw new RuntimeException("Must contain at least 2 groups");
        }
        int startGroupIndex = 1;
        Matcher matcher = SERIAL_NUMBER_GROUP_PATTERN.matcher(groups[startGroupIndex]);
        if (!matcher.matches()) {
            throw new RuntimeException("First group did not match the pattern");
        }
        String rawSerialNumber = new BigInteger(matcher.group(1), 10).toString(16);
        // padding left with zeros
        String padding = "0".repeat(SERIAL_NUMBER_LENGTH);
        String serialNumber = padding.substring(rawSerialNumber.length()) + rawSerialNumber;
        String partNumber = convertStringToHex(matcher.group(2));
        String measurement = "";
        for (int i = startGroupIndex + 1; i < groups.length; i++) {
            String current = groups[i];
            if (current.startsWith(MEASUREMENT_GROUP_PREFIX)) {
                measurement = convertStringToHex(current.substring(MEASUREMENT_GROUP_PREFIX.length()));
                break;
            }
        }
        return reformatHexData(serialNumber + partNumber + measurement);
    }

    /**
     * Converts a hex string into Atol Online format.
     *
     * @param hexStr hexadecimal string
     * @return formatted string
     */
    private static String reformatHexData(String hexStr) {
        StringBuilder builder = new StringBuilder("44 4d");
        char[] chars = hexStr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i % 2 == 0) {
                builder.append(' ');
            }
            builder.append(chars[i]);
        }
        return builder.toString();
    }

    /**
     * Converts ASCII encoded string into hexadecimal format.
     *
     * @param str string
     * @return hexadecimal string
     */
    public static String convertStringToHex(String str) {
        char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
        byte[] bytes = str.getBytes(StandardCharsets.US_ASCII);
        // two chars form the hex value.
        char[] hex = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            // 1 byte = 8 bits,
            // upper 4 bits is the first half of hex
            // lower 4 bits is the second half of hex
            // combine both and we will get the hex value, 0A, 0B, 0C
            int v = bytes[j] & 0xFF;               // byte widened to int, need mask 0xff
            // prevent sign extension for negative number
            hex[j * 2] = HEX_ARRAY[v >>> 4];       // get upper 4 bits
            hex[j * 2 + 1] = HEX_ARRAY[v & 0x0F];  // get lower 4 bits
        }
        return new String(hex);
    }
}
