package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.ui.AutoCompleteSupplier;

/**
 * a parser that processes information related to autocomplete. Does not really return a string so it does not extend
 * parser
 */
public class AutoCompleteParser {
    public static final String HIDE_COMMAND = "HIDE";
    public static final String SHOW_COMMAND = "SHOW";

    /**
     * tells how ghost should function, with the essential information for this purpose
     * @param text
     * @param caret
     * @return String arr where:
     *     arr[0] = hide or show ghost instruction,
     *     arr[1] = next suggestion,
     *     arr[2] = string of int start,
     *     arr[3] = substring of next to be shown
     */
    public static String[] command(String text, int caret) {
        final String[] show = {SHOW_COMMAND, null, null, null};
        final String[] hide = {HIDE_COMMAND, null, null, null};
        if (text == null) {
            text = "";
        }

        int[] bounds = getCurrentTokenBounds(text, caret);
        int start = bounds[0];
        int end = bounds[1];
        String token = text.substring(start, end);
        String next = null;
        String prefix = text.substring(start, end);
        String tail = null;
        int firstSpace = text.indexOf(' ');

        if (token.indexOf('/') >= 0) {
            return hide;
        }

        if (isParamsArea(firstSpace, text, caret)
                && (containsAdd(text) || containsSort(text))) {
            //case of having a space after add or sort

            assert (text.contains("add") || text.contains("sort"));

            next = AutoCompleteSupplier.giveAddSortParams(text);

            if (isEndWithSpace(text)) {
                next = AutoCompleteSupplier.giveAddSortParams(text);
                tail = AutoCompleteSupplier.makeTail(next, prefix);
                if (tail == null || tail.isEmpty()) {
                    return hide;

                }
                String[] result = makeShowArray(show, next, start, tail);
                return result;
            }

        } else if (AutoCompleteParser.isParamsArea(firstSpace, text, caret)
                && AutoCompleteParser.isContainsEdit(text)) {

            if (AutoCompleteParser.isHideEditParams(text)) {
                //checks if the text field has a number, if no, do not suggest first
                return hide;
            } else if (AutoCompleteParser.isEndWithSpace(text)) {
                next = AutoCompleteSupplier.giveEditParams(text);
                tail = AutoCompleteSupplier.makeTail(next, prefix);
                if (tail == null || tail.isEmpty()) {
                    return hide;
                }
                String[] result = makeShowArray(show, next, start, tail);
                return result;
            }

        } else if (AutoCompleteParser.isNotContainSpace(text)) { //case of suggesting for command:
            // assertion text has no " "
            if (start >= end) { // empty token
                return hide;
            }

            if (start != end) { //added guarding rail so space at the end won't suggest "add"
                next = AutoCompleteSupplier.findBestMatch(prefix);
            }

            if (next == null || next.isEmpty() || next.equals(prefix)) {
                return hide;
            }

            tail = AutoCompleteSupplier.makeTail(next, prefix); //
            String[] result = makeShowArray(show, next, start, tail);
            return result;
        }
        return null;
    }

    /**
     * abstraction for updating result array for command
     * @param arr hide or show arr
     * @param next suggestion
     * @param start start postion of next token
     * @param tail substring of next that is to be shown.
     * @return String[] command array
     */
    private static String[] makeShowArray(String[] arr, String next, int start, String tail) {
        arr[1] = next;
        arr[2] = Integer.toString(start);
        arr[3] = tail;
        return arr;
    }

    /**
     * A helper function that reads the starting and ending position of the text input. Returns a length-2 array
     * containining the position of start and end of the word being tpyed
     * @param t text
     * @param caret position of caret
     * @return
     */
    public static int[] getCurrentTokenBounds(String t, int caret) {
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
    public static boolean isParamsArea(int firstSpace, String text, int caret) {
        return firstSpace != -1 && caret >= firstSpace + 1;
    }

    /**
     * checks if string has add
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
     * check if string has edit
     * @param text
     * @return
     */
    public static boolean isContainsEdit(String text) {
        return text.contains(EditCommand.COMMAND_WORD);
    }

    /**
     * checks if the text after "edit" not has number or ends with number, either case, do not suggest param signature
     * @param text
     * @return
     */
    public static boolean isHideEditParams(String text) {
        return !text.matches(".*\\d.*") || Character.isDigit(text.charAt(text.length() - 1));
    }

    /**
     * checks if string ends with space
     * @param text
     * @return
     */
    public static boolean isEndWithSpace(String text) {
        return text.endsWith(" ");
    }

    /**
     * checks does string not contain space
     * @param text
     * @return
     */
    public static boolean isNotContainSpace(String text) {
        return !text.contains(" ");
    }

}
