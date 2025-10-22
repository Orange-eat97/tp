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

    private static final List<String> PARAMS = List.of("n/", "p/", "e/", "a/", "t/");

    /**
     * returns the match in the list of commands for ghost
     * @param input
     *
     **/
    public static String findBestMatch(String input) {
        return COMMANDS.stream().filter(x->x.startsWith(input)).findFirst().orElse("");
    }

    /**
     * method called to return list of params
     * @return current version of param list
     */
    public static List<String> paramList() {
        return PARAMS;
    }

    /**
     * public method fed into ghost to retrieve the param signature.
     * assertion: only called when command word is add or sort
     * @param text
     * @return string of next param signature
     */
    public String giveParam(String text) {
        String result = null;
        for (int i = 0; i < PARAMS.size(); i++) {
            if (!text.contains(PARAMS.get(i))) {
                result = PARAMS.get(i);
                break;
            }
        }
        return result;
    }
}
