package seedu.address.testutil;

import java.util.List;

/**
 * A utility class containing a list of {@code String} commands to be used in tests.
 */
public class TypicalCommands {
    public static final String ADD_PERSON_COMMAND = "add n/John Doe p/98765432 e/johnd@example.com a/311, "
            + "Clementi Ave 2, #02-25 t/volunteer t/owesMoney";
    public static final String DELETE_PERSON_COMMAND = "delete 1";
    public static final String LIST_COMMAND = "list";
    public static final List<String> LIST_OF_COMMANDS = List.of(ADD_PERSON_COMMAND,
            DELETE_PERSON_COMMAND, LIST_COMMAND);
}
