package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCommands.ADD_PERSON_COMMAND;
import static seedu.address.testutil.TypicalCommands.DELETE_PERSON_COMMAND;
import static seedu.address.testutil.TypicalCommands.LIST_COMMAND;
import static seedu.address.testutil.TypicalCommands.LIST_OF_COMMANDS;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;



public class StringUtilTest {

    //---------------- Tests for isNonZeroUnsignedInteger --------------------------------------

    @Test
    public void isNonZeroUnsignedInteger() {

        // EP: empty strings
        assertFalse(StringUtil.isNonZeroUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));

        // EP: zero as prefix
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));

        // EP: signed numbers
        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0")); // Spaces in the middle

        // EP: number larger than Integer.MAX_VALUE
        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isNonZeroUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }


    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullWord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
                -> StringUtil.containsWordIgnoreCase("typical sentence", null));
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter cannot be empty", ()
                -> StringUtil.containsWordIgnoreCase("typical sentence", "  "));
    }

    @Test
    public void containsWordIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter should be a single word", ()
                -> StringUtil.containsWordIgnoreCase("typical sentence", "aaa BBB")
        );
    }

    @Test
    public void containsWordIgnoreCase_nullSentence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsWordIgnoreCase(null, "abc")
        );
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bb")); // Sentence word bigger than query word
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsWordIgnoreCase("Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }

    @Test
    public void containsWordPrefixIgnoreCase_validInputs_correctResult() {
        // Empty sentence
        assertFalse(StringUtil.containsWordPrefixIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordPrefixIgnoreCase("    ", "123"));

        // Query prefix bigger than sentence word
        assertFalse(StringUtil.containsWordPrefixIgnoreCase("aaa bbb ccc", "bbbb"));

        // Matches prefix in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordPrefixIgnoreCase("aaa bBb ccc", "Bb")); // First word (boundary case)
        assertTrue(StringUtil.containsWordPrefixIgnoreCase("aaa bBb ccc@1", "CCc@")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordPrefixIgnoreCase("  AAA   bBb   ccc  ", "aa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsWordPrefixIgnoreCase("Aaa", "aa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsWordPrefixIgnoreCase("aaa bbb ccc", "  cc  ")); // Leading/trailing spaces


    }

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertTrue(StringUtil.getDetails(new FileNotFoundException("file not found"))
            .contains("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
                -> StringUtil.getDetails(null)
        );
    }

    //---------------- Tests for getAllElements  --------------------------------------

    /*
     * Equivalence Partitions: null, empty string, multiple spaces between elements, trailing and leading spaces
     */

    @Test
    public void getAllElements_nullString_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.getAllElements(null));
    }

    @Test
    public void getAllElements_emptyString_returnsEmptyArray() {
        assertArrayEquals(new String[]{}, StringUtil.getAllElements(""));
    }

    @Test
    public void getAllElements_singleElement_returnsSingleElementArray() {
        assertArrayEquals(new String[]{"hello"},
                StringUtil.getAllElements("hello"));
    }

    @Test
    public void getAllElements_multipleElementsWithMultipleSpaces_returnsAllElements() {
        assertArrayEquals(new String[]{"a", "b", "c"},
                StringUtil.getAllElements("a   b    c"));
    }

    @Test
    public void getAllElements_leadingAndTrailingSpaces_ignoresBoth() {
        assertArrayEquals(new String[]{"hello", "world"},
                StringUtil.getAllElements("  hello world  ")
        );
    }

    @Test
    public void getAllElements_onlySpaces_returnsEmptyArray() {
        assertArrayEquals(new String[]{},
                StringUtil.getAllElements("     ")
        );
    }

    //---------------- Tests for formatNumberedListWithHighlight --------------------------------------

    /*
     * Equivalence Partitions: null, empty list, list with single object, list with multiple objects, index out of range
     */

    // Null list
    @Test
    public void formatNumberedListWithHighlight_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
                -> StringUtil.formatNumberedListWithHighlight(null, 0)
        );
    }

    // Empty List
    @Test
    public void formatNumberedListWithHighlight_emptyList_returnEmptyString() {
        assertEquals("", StringUtil.formatNumberedListWithHighlight(new ArrayList<>(), 0));
    }

    // List with single object
    @Test
    public void formatNumberedListWithHighlight_singleElement_highlightsOnlyElement() {
        assertEquals("*1 list\n",
                StringUtil.formatNumberedListWithHighlight(List.of(LIST_COMMAND), 0)
        );
    }

    // List with multiple objects
    @Test
    public void formatNumberedListWithHighlight_multipleElements_highlightsMiddleElement() {
        String expectedResult = " 1 " + ADD_PERSON_COMMAND + "\n"
                + "*2 " + DELETE_PERSON_COMMAND + "\n"
                + " 3 " + LIST_COMMAND + "\n";

        assertEquals(expectedResult,
            StringUtil.formatNumberedListWithHighlight(LIST_OF_COMMANDS, 1));
    }

    // Index out of range
    @Test
    public void formatNumberedListWithHighlight_negativeIndex_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, ()
                -> StringUtil.formatNumberedListWithHighlight(LIST_OF_COMMANDS, -1)
        );
    }

    @Test
    public void normalizeInnerSpaces_leadingTrailingInnerSpaces_normalized() {
        assertEquals("This is a test string",
                StringUtil.normalizeInnerSpaces("  This   is  a    test string  "));
    }

    @Test
    public void normalizeInnerSpaces_nullInput_returnsNull() {
        assertEquals(null, StringUtil.normalizeInnerSpaces(null));
    }

    @Test
    public void normalizeInnerSpaces_onlyWhitespace_returnsEmptyString() {
        assertEquals("",
                StringUtil.normalizeInnerSpaces("     \t   \n  "));
    }

    @Test
    public void standardiseName_null_returnsNull() {
        assertEquals(null, StringUtil.standardiseName(null));
    }

    @Test
    public void standardiseName_allUpperCase_returnsLowerCase() {
        assertEquals("john doe", StringUtil.standardiseName("JOHN DOE"));
    }

    @Test
    public void standardiseName_includeSpecialChars_returnsLowerCase() {
        assertEquals("john doe", StringUtil.standardiseName("john'- doe"));
    }

    @Test
    public void standardiseName_upperCaseWithSpecialChars_returnsLowerCase() {
        assertEquals("john doe", StringUtil.standardiseName("JOHN'- DOE"));
    }

    @Test
    public void removeAllWhiteSpace_null_returnsNull() {
        assertEquals(null, StringUtil.removeAllWhitespace(null));
    }

    @Test
    public void removeAllWhiteSpace_noChange_returnsSameString() {
        assertEquals("12345678", StringUtil.standardiseName("12345678"));
    }

    @Test
    public void removeAllWhiteSpace_withSpaces_returnsStringWithoutSpaces() {
        assertEquals("12345678", StringUtil.removeAllWhitespace("  1 23456  78 "));
    }
}
