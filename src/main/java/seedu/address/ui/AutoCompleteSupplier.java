package seedu.address.ui;
import java.util.List;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.AutoCompleteParser;

/**
 * this class handles the logic of matching input, returns a string of the ghost
 * **/

public class AutoCompleteSupplier {
    private static final List<String> COMMANDS = List.of(
            AddCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            SortCommand.COMMAND_WORD);


    /**
     * returns the match in the list of commands for ghost
     * @param input
     *
     **/
    public static String findBestMatch(String input) {
        return COMMANDS.stream().filter(x->x.startsWith(input)).findFirst().orElse("");
    }

    /**
     * give a param for either sort or add
     * @param text
     * @return
     */
    public static String giveAddSortFindParams(String text) {
        String next = null;
        if (AutoCompleteParser.containsAdd(text)) {
            next = giveParam(AddCommand.COMMAND_WORD, text, AddCommand.PARAMS);
            //get the suggested next param
        } else if (AutoCompleteParser.containsSort(text)) {
            next = giveParam(SortCommand.COMMAND_WORD, text, SortCommand.PARAMS);
        } else if (AutoCompleteParser.containsFind(text)) {
            next = giveParam(FindCommand.COMMAND_WORD, text, FindCommand.PARAMS);
        }
        return next;
    }

    /**
     * returns a param for edit command
     * @param text
     * @return
     */
    public static String giveEditParams(String text) {
        return giveParam(EditCommand.COMMAND_WORD, text, getParamList("edit"));
    }

    /**
     * public method fed into ghost to retrieve the param signature.
     * assertion: only called when command word is add or sort
     * @param text
     * @return string of next param signature
     */
    public static String giveParam(String command, String text, List<String> params) {
        String result = null;
        int prefixContaied = 0;
        for (int i = 0; i < params.size(); i++) {
            if (!text.contains(" " + params.get(i))) {
                //only finds prefix with a space leading it + triggers when at least some param is contained
                result = params.get(i);
                break;
            } else {
                prefixContaied += 1;
            }
        }

        if (prefixContaied == params.size()
                && command != SortCommand.COMMAND_WORD
                && command != EditCommand.COMMAND_WORD
                && command != FindCommand.COMMAND_WORD) {
            return "t/";
        } else {
            return result;
        }
    }

    /**
     * abstraction of making the correct suggestion tail. Added new param
     * @param suggestion full suggestion returned by autocompletesupplier
     * @param prefix existing string in the commandbox
     */
    public static String makeTail(String command, String suggestion, String prefix) {
        if (suggestion == null) { //if suggestion is null, just return an empty string
            return "";
        }
        if (prefix == null) { //if prefix is null, keep it as a empty string for matching
            prefix = "";
        }

        List<String> temp = getParamList(command);
        String possibleParams = temp.stream().map(param -> param.substring(0, 1)).toString();

        if (prefix.length() == 1 && suggestion.length() == 2 && suggestion.charAt(1) == '/'
                && isCharInString(possibleParams, prefix)
                && isCharInString(possibleParams, suggestion)) {
            return "/";
        }

        int prefixLength = prefix.length();
        if (suggestion.length() <= prefixLength) { //case of having "e" as suggestion while prefix is "delet". Wrong
            //logic passed by caller
            return "";
        }

        if (!suggestion.startsWith(prefix)) { //case of wrong suggestion, eg having p/ suggested for "c"
            return "";
        }

        return suggestion.substring(prefixLength); //assertion: suggestion definitely starts with prefix
    }

    /**
     * makeTail that takes a different signature, specifically for suggesting command
     * @param suggestion
     * @param prefix
     * @return
     */
    public static String makeTail(String suggestion, String prefix) {
        if (suggestion == null) { //if suggestion is null, just return an empty string
            return "";
        }
        if (prefix == null) { //if prefix is null, keep it as a empty string for matching
            prefix = "";
        }

        int prefixLength = prefix.length();
        if (suggestion.length() <= prefixLength) { //case of having "e" as suggestion while prefix is "delet". Wrong
            //logic passed by caller
            return "";
        }

        if (!suggestion.startsWith(prefix)) { //case of wrong suggestion, eg having p/ suggested for "c"
            return "";
        }

        return suggestion.substring(prefixLength); //assertion: suggestion definitely starts with prefix
    }

    /**
     * abstraction for getting paramList
     * @param command command word
     * @return param list of each command class
     */
    private static List<String> getParamList(String command) {
        switch (command) {
        case "add": return AddCommand.PARAMS;
        case "sort": return SortCommand.PARAMS;
        case "edit": return EditCommand.PARAMS;
        case "find": return FindCommand.PARAMS;
        default: return null;
        }
    }

    /**
     * abstraction used to check if prefix and suggestion are among possible param signatures
     * @param checkList paramlist
     * @param check string to be checked
     * @return boolean
     */
    private static boolean isCharInString(String checkList, String check) {
        return checkList.indexOf(check.charAt(0)) >= 0;
    }

}
