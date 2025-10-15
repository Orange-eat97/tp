package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommandHistory {
    private final ObservableList<String> commandHistory;
    private int currentIndex;

    public CommandHistory() {
        this.commandHistory = FXCollections.observableArrayList();
        this.currentIndex = 0;
    }

    public boolean hasCommands() {
        return !commandHistory.isEmpty();
    }

    public void addCommand(String commandText) {
        commandHistory.add(commandText);
        currentIndex = this.commandHistory.size() - 1;
    }

    public boolean hasPrevious() {
        return currentIndex > 0;
    }

    public String getPreviousCommand() {
        if (!hasCommands()) {
            return "";
        }

        if (hasPrevious()) {
            currentIndex--;
        }

        return this.commandHistory.get(currentIndex);
    }

    public boolean hasNext() {
        return currentIndex < this.commandHistory.size() - 1;
    }

    public String getNextCommand() {
        if (!hasCommands()) {
            return "";
        }

        if (hasNext()) {
            currentIndex++;
        }

        return this.commandHistory.get(currentIndex);
    }
}
