package seedu.address.testutil;

import seedu.address.commons.util.StringUtil;

import java.util.List;

public class TypicalCommandHistory {

    private static final String COMMAND_HISTORY_HEADING = "Command History:\n";

    public static String ONE_COMMAND_COMMAND_HISTORY = COMMAND_HISTORY_HEADING + StringUtil.formatNumberedListWithHighlight(
            List.of(TypicalCommands.ADD_PERSON_COMMAND), 0);
    public static String THREE_COMMANDS_COMMAND_HISTORY = COMMAND_HISTORY_HEADING + StringUtil.formatNumberedListWithHighlight(
            TypicalCommands.LIST_OF_COMMANDS, 2);

}
