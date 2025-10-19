package seedu.address.testutil;

import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * A utility class containing command history strings to be used in tests.
 */
public class TypicalCommandHistory {

    private static final String COMMAND_HISTORY_HEADING = "Command History:\n";

    public static final String NO_COMMAND_COMMAND_HISTORY =
            COMMAND_HISTORY_HEADING + "No commands yet";

    public static final String ONE_COMMAND_COMMAND_HISTORY =
            COMMAND_HISTORY_HEADING + StringUtil.formatNumberedListWithHighlight(
                    List.of(TypicalCommands.ADD_PERSON_COMMAND), 0);

    public static final String THREE_COMMANDS_COMMAND_HISTORY =
            COMMAND_HISTORY_HEADING + StringUtil.formatNumberedListWithHighlight(
                TypicalCommands.LIST_OF_COMMANDS, 2);

}
