package utilities;

import java.util.Base64;

public class Redact {

    /**
     * Encodes the given input string into a Base64 encoded string.
     *
     * @param input the input string to be encoded.
     * @return the Base64 encoded string.
     */
    public static String encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    /**
     * Encodes the given input byte array into a Base64 encoded string.
     *
     * @param inputBytes the input byte array to be encoded.
     * @return the Base64 encoded string.
     */
    public static String encode(byte[] inputBytes) {
        return Base64.getEncoder().encodeToString(inputBytes);
    }


    /**
     * Decodes the given Base64 encoded string into a decoded string.
     *
     * @param encodedString the Base64 encoded string to be decoded.
     * @return the decoded string.
     */
    public static String decode(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }

    /**
     * Decodes the given Base64 encoded string into a decoded byte array.
     *
     * @param encodedString the Base64 encoded string to be decoded.
     * @return the decoded byte array.
     */
    public static byte[] decodeToBytes(String encodedString) {
        return Base64.getDecoder().decode(encodedString);
    }
}
