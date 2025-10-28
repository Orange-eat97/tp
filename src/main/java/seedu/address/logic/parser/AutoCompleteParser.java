package seedu.address.logic.parser;

import javafx.scene.control.TextField;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.SortCommand;

/**
 * a parser that processes information related to autocomplete. Does not really return a string so it does not extend
 * parser
 */
public class AutoCompleteParser {

    /**
     * A helper function that reads the starting and ending position of the text input. Returns a length-2 array
     * containining the position of start and end of the word being tpyed
     *
     * @param
     */
    public static int[] getCurrentTokenBounds(TextField commandTextField) {
        String t = commandTextField.getText();
        if (t == null) {
            t = "";
        }
        int caret = commandTextField.getCaretPosition();
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
     * method for checking is string has add or sort key word
     * @param text
     * @return
     */
    public static boolean isAddSort(String text) {
        return text.contains(AddCommand.COMMAND_WORD) || text.contains(SortCommand.COMMAND_WORD);
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
