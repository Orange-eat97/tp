package seedu.address.logic.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.logic.AutoCompleteSupplier;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.SortCommand;

/**
 * a parser that processes information related to autocomplete. Does not really return a string so it does not extend
 * parser
 */
public class AutoCompleteParser {
    public static final String HIDE_COMMAND = "HIDE";
    public static final String SHOW_COMMAND = "SHOW";

    private static Logger hideLogger = Logger.getLogger("hideLogger");

    /**
     * tells how ghost should function, with the essential information for this purpose. Cases:
     *     1, text has no space: must be suggesting for a command
     *     2, text has a space but does not end with it, eg "add n/", "add j", "add n" -> throws issue to maketail,
     *     if tail is not empty (case of add n / add p...), shows "/". If tail is empty ->hide
     *     3, text has a space and ends with it: "add "
     *
     * @param text
     * @param caret
     * @return String arr where:
     *     arr[0] = hide or show ghost instruction,
     *     arr[1] = next suggestion,
     *     arr[2] = string of int start,
     *     arr[3] = substring of next to be shown
     */
    public static String[] command(String text, int caret) {
        if (text == null) {
            text = "";
        }

        int[] bounds = getCurrentTokenBounds(text, caret);
        int start = bounds[0];
        int end = bounds[1];
        String next = null;
        String prefix = text.substring(start, end);
        String tail = null;
        int firstSpace = text.indexOf(' ');
        int lastSpace = (caret > 0) ? text.lastIndexOf(' ', caret - 1) : -1;
        int segStart = (lastSpace == -1) ? 0 : lastSpace + 1;

        String caretChunk = text.substring(segStart, Math.min(caret, text.length())); //finds a substring that starts
        //after the latest space, ie "add n/" -> "/n"

        if (caretChunk.indexOf('/') >= 0) { //case when current text has something like "add n/"-> hide,
            //case like "add n/james p" -> "p", so go to the other blocks
            return makeCommandsArray(null, 0, null);
        }

        if (isParamsArea(firstSpace, text, caret) //case like "add " -> to suggest a param signature
                && (containsAdd(text) || containsSort(text) || containsFind(text))) {

            assert (text.contains("add") || text.contains("sort") || text.contains("find"));

            next = AutoCompleteSupplier.giveAddSortFindParams(text);
            tail = AutoCompleteSupplier.makeTail("add", next, prefix); //add sort and find have the same param
            //list

            return makeCommandsArray(next, start, tail);

        } else if (isParamsArea(firstSpace, text, caret)
                && isContainsEdit(text)) { //case of edit

            next = AutoCompleteSupplier.giveEditParams(text);
            tail = AutoCompleteSupplier.makeTail("edit", next, prefix);

            if (isHideEditParams(text)) { //case when text has no index, hide
                hideLogger.log(Level.INFO, "Hiding edit text =" + text + " tail = " + tail);
                return makeCommandsArray(null, 0, null);
            } else if (isEndWithSpace(text)) { //case of "edit 1 " -> suggest param signature
                return makeCommandsArray(next, start, tail);
            } else { //case of "edit 1" -> still suggest
                return makeCommandsArray(next, start, tail);
            }

        } else if (isNotContainSpace(text)) { //case of command word

            if (start != end) {
                next = AutoCompleteSupplier.findBestMatch(prefix);
            }

            if (nullOrEmpty(next) || next.equals(prefix) || start >= end) {
                hideLogger.log(Level.INFO, "invalid next=" + next + " prefix = " + prefix);
                return makeCommandsArray(null, 0, null);
            }

            tail = AutoCompleteSupplier.makeTail(next, prefix);

            return makeCommandsArray(next, start, tail);
        }

        return makeCommandsArray(null, 0, null);
    }

    /**
     * abstraction for checking if suggestion or tail is null or empty
     * @param text
     * @return
     */
    private static boolean nullOrEmpty(String text) {
        return (text == null || text.isEmpty());
    }

    /**
     * abstraction for updating result array for command
     * @param next suggestion
     * @param start start postion of next token
     * @param tail substring of next that is to be shown.
     * @return String[] command array
     */
    private static String[] makeCommandsArray(String next, int start, String tail) {
        final String[] show = {SHOW_COMMAND, null, null, null};
        final String[] hide = {HIDE_COMMAND, null, null, null};

        if (nullOrEmpty(next) || nullOrEmpty(tail)) {
            return hide;
        } else {
            String[] arr = show;
            arr[1] = next;
            arr[2] = Integer.toString(start);
            arr[3] = tail;
            return arr;
        }
    }

    /**
     * A helper function that reads the starting and ending position of the text input. Returns a length-2 array
     * containining the position of start and end of the word being tpyed
     * @param t text
     * @param caret position of caret
     * @return
     */
    private static int[] getCurrentTokenBounds(String t, int caret) {
        if (t == null) {
            t = "";
        }
        if (caret < 0) {
            caret = 0;
        }
        if (caret > t.length()) {
            caret = t.length();
        }
        if (t.isEmpty() || caret == 0) {
            return new int[]{caret, caret};
        }

        int i = caret - 1;
        while (i >= 0) {
            char c = t.charAt(i);
            if (Character.isLetterOrDigit(c) || c == '_' || c == ':' || c == '-') {
                i--;
            } else {
                break;
            }
        }
        int start = i + 1;
        return new int[]{start, caret};
    }


    /**
     * checks if the caret is in the mid of keying in params
     * @param text
     * @param caret
     * @return
     */
    private static boolean isParamsArea(int firstSpace, String text, int caret) {
        return firstSpace != -1 && caret >= firstSpace + 1;
    }

    /**
     * checks if string has add command commandword
     * @param text
     * @return
     */
    public static boolean containsAdd(String text) {
        return text.contains(AddCommand.COMMAND_WORD);
    }

    /**
     * checks if string has sort
     * @param text
     * @return
     */
    public static boolean containsSort(String text) {
        return text.contains(SortCommand.COMMAND_WORD);
    }

    /**
     * checks if string contains find
     * @param text
     * @return
     */
    public static boolean containsFind(String text) {
        return text.contains(FindCommand.COMMAND_WORD);
    }

    /**
     * check if string has edit
     * @param text
     * @return
     */
    private static boolean isContainsEdit(String text) {
        return text.contains(EditCommand.COMMAND_WORD);
    }

    /**
     * checks if the text after "edit" not has number or ends with number, either case, do not suggest param signature
     * @param text
     * @return
     */
    private static boolean isHideEditParams(String text) {
        return !text.matches(".*\\d.*") || Character.isDigit(text.charAt(text.length() - 1));
    }

    /**
     * checks if string ends with space
     * @param text
     * @return
     */
    private static boolean isEndWithSpace(String text) {
        return text.endsWith(" ");
    }

    /**
     * checks does string not contain space
     * @param text
     * @return
     */
    private static boolean isNotContainSpace(String text) {
        return !text.contains(" ");
    }

}
