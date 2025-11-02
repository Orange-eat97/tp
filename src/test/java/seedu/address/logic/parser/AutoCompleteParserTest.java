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
    public void command_noSpaceAndSubstringOfCommand_showCommand() {
        String text = "ad";
        int caret = text.length(); // 2
        String[] expected = show("add", 0, "d");
        assertArrayEquals(expected, AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text is full command + !ends with space = hide
     */
    @Test
    public void command_noSpaceCompleteCommand_hide() {
        String text = "add";
        int caret = text.length(); // 2
        assertArrayEquals(hide(), AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text is full command + ends with space = show param
     */
    @Test
    public void command_spaceAfterCommand_showParam() {
        String text = "add ";
        int caret = text.length();
        String[] expected = show("n/", caret, "n/");
        assertArrayEquals(expected, AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text has command + letter for param signature + !ends with space = show /
     */
    @Test
    public void command_afterParamLetter_showSlash() {
        String text = "add n";
        int caret = text.length();
        String[] expected = show("n/", 4, "/");
        assertArrayEquals(expected, AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text has command + !letter for param signature + !ends with space = hide
     */
    @Test
    public void command_afterInvalidLetter_hide() {
        String text = "add q";
        int caret = text.length();
        assertArrayEquals(hide(), AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text has edit + has index + ends with space = show param
     */
    @Test
    public void command_spaceAfterEditWithIndex_showParam() {
        String text = "edit 1 ";
        int caret = text.length();
        String[] expected = show("n/", caret, "n/");
        assertArrayEquals(expected, AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text has edit + has index + !ends with space = hide
     */
    @Test
    public void coomand_afterEditNoIndex_hide() {
        String text = "edit 1";
        int caret = text.length();
        assertArrayEquals(hide(), AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text has edit + !has index + !ends with space = hide
     */
    @Test
    public void command_afterEditAndLetterNoIndex_hide() {
        String text = "edit n";
        int caret = text.length();
        assertArrayEquals(hide(), AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text has edit + !has index + ends with space = hide
     */
    @Test
    public void command_afterEditAndLetterEndsSpaceNoIndex_hide() {
        String text = "edit n ";
        int caret = text.length();
        assertArrayEquals(hide(), AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = invalid prefix = hide
     */
    @Test
    public void command_invalidPrefix_hide() {
        String text = "AD";
        int caret = text.length();
        assertArrayEquals(hide(), AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text ends with / = hide
     */
    @Test
    public void command_textEndWithSlash_hide() {
        String text = "add n/";
        int caret = text.length();
        assertArrayEquals(hide(), AutoCompleteParser.command(text, caret));
    }

    /**
     * EP = text = null = hide
     */
    @Test
    void command_nullText_hide() {
        assertArrayEquals(hide(), AutoCompleteParser.command(null, 0));
    }

}
