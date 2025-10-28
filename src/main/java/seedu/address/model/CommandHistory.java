package seedu.address.model;

import seedu.address.commons.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Keeps track of all commands entered in the current session
 */
public class CommandHistory {

    private static final String COMMAND_HISTORY_HEADING = "Command History:\n";

    private final Queue<String> commandHistory;
    private int queueIndex;

    /**
     * Creates a CommandHistory from scratch
     */
    public CommandHistory() {
        this.commandHistory = new LinkedList<>();
        this.queueIndex = 0;
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
        if (commandHistory.size() > 5) {
            commandHistory.poll();
        }
        queueIndex = -1;
    }

    /**
     * Returns true if the command history has not been navigated yet.
     *
     * <p>This is the case when the current index has not been set,
     *  i.e., the user has not pressed the up or down key to cycle through the command history.</p>*
     *
     *  @return true if the command history is being navigated for the first time, false otherwise
     */
    private boolean isInitialPress() {
        return queueIndex == -1;
    }

    /**
     * Returns true if there exists an older command in the queue before the command at the current index
     */
    public boolean hasPrevious() {
        return queueIndex > 0;
    }

    /**
     * Returns the previous command in the history, i.e. the older command entered after the current one.
     *
     * <p>If the command history is empty, returns null
     * If the current command is the oldest in the history, returns the newest command.
     * Otherwise, returns the command immediately before the current one and moves the current index back by 1.</p>
     *
     * @return the previous command, the newest command if at the oldest command, or null if no commands exist
     */
    public String getPreviousCommand() {
        if (!hasCommands()) {
            return null;
        }

        if (isInitialPress()) {
            queueIndex = this.commandHistory.size() - 1;
        } else if (hasPrevious()) {
            queueIndex--;
        } else {
            queueIndex = this.commandHistory.size() - 1;
        }

        List<String> commandHistoryList = new ArrayList<>(commandHistory);
        return commandHistoryList.get(queueIndex);
    }

    /**
     * Returns true if there exists a newer command after the command at the current index
     */
    public boolean hasNext() {
        return queueIndex < this.commandHistory.size() - 1;
    }

    /**
     * Returns the next command in the history, i.e. the newer command entered after the current one.
     *
     * <p>If the command history is empty, return null
     * If the current command is the newest in the history, returns the oldest command.
     * Otherwise, returns the command immediately after the current one and moves the current index forward by 1.</p>
     *
     * @return the next command, the oldest command if at the newest command, or null if no commands exist
     */
    public String getNextCommand() {
        if (!hasCommands()) {
            return null;
        }

        if (isInitialPress()) {
            queueIndex = this.commandHistory.size() - 1;
        } else if (hasNext()) {
            queueIndex++;
        } else {
            queueIndex = 0;
        }

        List<String> commandHistoryList = new ArrayList<>(commandHistory);
        return commandHistoryList.get(queueIndex);
    }

    /**
     * Returns all commands in the command history in a single string
     *
     * <p>Neatly arranges all commands in a numbered list,
     * from most recent command at the top to oldest command at the bottom,
     * with an asterisk next to the current command</p>
     *
     * @return the list of all commands in the command history
     */
    public String toString() {
        if (commandHistory.isEmpty()) {
            return COMMAND_HISTORY_HEADING + "No commands yet";
        }
        List<String> commandHistoryList = new ArrayList<>(commandHistory);
        Collections.reverse(commandHistoryList);
        int highlightIndex = queueIndex == -1 ? 0 : commandHistory.size() - 1 - queueIndex;
        return COMMAND_HISTORY_HEADING
                + StringUtil.formatNumberedListWithHighlight(commandHistoryList, highlightIndex);
    }
}
