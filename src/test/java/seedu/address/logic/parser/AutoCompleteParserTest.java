package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class AutoCompleteParserTest {

    public static final String HIDE_COMMAND = "HIDE";
    public static final String SHOW_COMMAND = "SHOW";

    private static String[] show(String next, int start, String tail) {
        return new String[] { SHOW_COMMAND, next, Integer.toString(start), tail };
    }
    private static String[] hide() {
        return new String[] { HIDE_COMMAND, null, null, null };
    }

    /**
     * EP = text is substring of command + !ends with space = show command
     */
    @Test
    public void noSpaceShowCommand() {
        String text = "ad";
        int caret = text.length(); // 2
        String[] expected = show("add", 0, "d");
        assertArrayEquals(expected, AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text is full command + !ends with space = hide
     */
    @Test
    public void noSpaceHide() {
        String text = "add";
        int caret = text.length(); // 2
        assertArrayEquals(hide(), AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text is full command + ends with space = show param
     */
    @Test
    public void spaceAfterCommandShowParam() {
        String text = "add ";
        int caret = text.length();
        String[] expected = show("n/", caret, "n/");
        assertArrayEquals(expected, AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text has command + letter for param signature + !ends with space = show /
     */
    @Test
    public void afterParamLetterShowSlash() {
        String text = "add n";
        int caret = text.length();
        String[] expected = show("n/", 4, "/");
        assertArrayEquals(expected, AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text has command + !letter for param signature + !ends with space = hide
     */
    @Test
    public void afterInvalidLetterHide() {
        String text = "add q";
        int caret = text.length();
        assertArrayEquals(hide(), AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text has edit + has index + ends with space = show param
     */
    @Test
    public void spaceAfterEditWithIndexShowParam() {
        String text = "edit 1 ";
        int caret = text.length();
        String[] expected = show("n/", caret, "n/");
        assertArrayEquals(expected, AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text has edit + has index + !ends with space = hide
     */
    @Test
    public void afterEditNoIndexHide() {
        String text = "edit 1";
        int caret = text.length();
        assertArrayEquals(hide(), AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text has edit + !has index + !ends with space = hide
     */
    @Test
    public void afterEditAndLetterNoIndexHide() {
        String text = "edit n";
        int caret = text.length();
        assertArrayEquals(hide(), AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text has edit + !has index + ends with space = hide
     */
    @Test
    public void afterEditAndLetterEndsSpaceNoIndexHide() {
        String text = "edit n ";
        int caret = text.length();
        assertArrayEquals(hide(), AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = invalid prefix = hide
     */
    @Test
    public void invalidPrefixHide() {
        String text = "AD";
        int caret = text.length();
        assertArrayEquals(hide(), AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text ends with / = hide
     */
    @Test
    public void textEndWithSlashHide() {
        String text = "add n/";
        int caret = text.length();
        assertArrayEquals(hide(), AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text = null = hide
     */
    @Test
    void nullTextHide() {
        assertArrayEquals(hide(), AutoCompleteParser.command(null, 0));
    }

}
