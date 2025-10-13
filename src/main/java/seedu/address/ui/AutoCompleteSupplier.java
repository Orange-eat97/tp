package seedu.address.ui;

import java.util.List;

/**
 * this class handles the logic of matching input, returns a string of the ghost
 * **/

public class AutoCompleteSupplier {
    private static final List<String> COMMANDS = List.of("list", "add", "delete", "edit", "find", "sort");

    /**
     * returns the match in the list of commands for ghost
     * @param input
     *
     **/
    public String findBestMatch(String input) {
        return COMMANDS.stream().filter(x->x.startsWith(input)).findFirst().orElse("");

    }
}
