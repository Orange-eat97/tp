package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Returns an array of strings if {@code s} represents a string of space-separated sub-strings
     * e.g. "1 2 3" or "a b c" <br>
     * @throws NullPointerException if {@code s} is null.
     */
    public static String[] getAllElements(String s) {
        requireNonNull(s);

        String trimmedString = s.trim();
        if (trimmedString.isEmpty()) {
            return new String[0];
        }

        return trimmedString.split("\\s+");
    }

    /**
     * Returns a string representing a list of strings in numbered point form
     * The current list element in focus has an asterisk (*) next to it
     * @throws NullPointerException if {@code listOfStrings} is null.
     * @throws IndexOutOfBoundsException if {@code currIndex} is out of bounds.
     */
    public static String formatNumberedListWithHighlight(List<String> strings, int currIndex) {
        requireNonNull(strings);

        if (strings.isEmpty()) {
            return "";
        }

        if (currIndex < 0 || currIndex >= strings.size()) {
            throw new IndexOutOfBoundsException("currIndex is out of range: " + currIndex);
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            String string = strings.get(i);
            int actualIndex = i + 1;
            String prefix = i == currIndex ? "*" + actualIndex : " " + actualIndex;
            String listItem = prefix + " " + string + "\n";
            result.append(listItem);
        }
        return result.toString();
    }
}
