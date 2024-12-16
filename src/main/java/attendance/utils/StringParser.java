package attendance.utils;

import attendance.error.ErrorMessages;
import java.util.Arrays;
import java.util.List;

public final class StringParser {

    private static final int SPLIT_LIMIT = -1;

    private StringParser() {

    }

    public static int parseToInt(final String value, final ErrorMessages errorMessages) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessages.getMessage());
        }
    }

    public static List<String> parseToTokens(final String value, final String delimiter) {
        return Arrays.stream(value.split(delimiter, SPLIT_LIMIT))
                .toList();
    }
}
