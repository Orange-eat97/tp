package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.StringUtil;

/**
 * Keeps track of all commands entered in the current session
 */
public class CommandHistory {

    private static final String COMMAND_HISTORY_HEADING = "Command History:\n";

    private final ObservableList<String> commandHistory;
    private int currentIndex;

    /**
     * Creates a CommandHistory from scratch
     */
    public CommandHistory() {
        this.commandHistory = FXCollections.observableArrayList();
        this.currentIndex = 0;
    }

    /**
     * Returns true if there are any commands in the command history
     */
    public boolean hasCommands() {
        return !commandHistory.isEmpty();
    }

    /**
     * Adds an entered command to the command history
     */
    public void addCommand(String commandText) {
        commandHistory.add(commandText);
        currentIndex = this.commandHistory.size() - 1;
    }

    /**
     * Returns true if there exists a command before the command at the current index
     */
    public boolean hasPrevious() {
        return currentIndex > 0;
    }

    /**
     * Returns the previous command in the history.
     *
     * <p>If the command history is empty, returns an empty string.
     * If the current command is the first in the history, returns the current command.
     * Otherwise, returns the command immediately before the current one and moves the cursor back.</p>
     *
     * @return the previous command, the current command if at the start, or an empty string if no commands exist
     */
    public String getPreviousCommand() {
        if (!hasCommands()) {
            return "";
        }

        if (hasPrevious()) {
            currentIndex--;
        }

        return this.commandHistory.get(currentIndex);
    }

    /**
     * Returns true if there exists a command after the command at the current index
     */
    public boolean hasNext() {
        return currentIndex < this.commandHistory.size() - 1;
    }

    /**
     * Returns the next command in the history.
     *
     * <p>If the command history is empty, return an empty string.
     * If the current command is the last in the history, returns the current command.
     * Otherwise, returns the command immediately after the current one and moves the cursor forward.</p>
     *
     * @return the next command, the current command if at the back, or an empty string if no commands exist
     */
    public String getNextCommand() {
        if (!hasCommands()) {
            return "";
        }

        if (hasNext()) {
            currentIndex++;
        }

        return this.commandHistory.get(currentIndex);
    }

    /**
     * Returns all commands in the command history in a single string
     * Neatly arranges all commands in a numbered list, with an asterisk next to the current command
     * @return the list of all commands in the command history
     */
    public String getCommandHistory() {
        if (commandHistory == null || commandHistory.isEmpty()) {
            return COMMAND_HISTORY_HEADING + "No commands yet";
        }
        return COMMAND_HISTORY_HEADING + StringUtil.formatNumberedListWithHighlight(commandHistory, currentIndex);
    }
}
