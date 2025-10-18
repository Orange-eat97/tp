package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCommandHistory.ONE_COMMAND_COMMAND_HISTORY;
import static seedu.address.testutil.TypicalCommandHistory.THREE_COMMANDS_COMMAND_HISTORY;
import static seedu.address.testutil.TypicalCommands.ADD_PERSON_COMMAND;
import static seedu.address.testutil.TypicalCommands.DELETE_PERSON_COMMAND;
import static seedu.address.testutil.TypicalCommands.LIST_COMMAND;
import static seedu.address.testutil.TypicalCommands.LIST_OF_COMMANDS;

import org.junit.jupiter.api.Test;

public class CommandHistoryTest {

    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor() {
        assertFalse(commandHistory.hasCommands());
        assertEquals("", commandHistory.getPreviousCommand());
        assertEquals("", commandHistory.getNextCommand());
    }

    @Test
    public void addCommand_singleCommand_updatesCommandHistory() {
        commandHistory.addCommand(ADD_PERSON_COMMAND);
        // check command has been added
        assertTrue(commandHistory.hasCommands());

        // should stay on same command
        assertEquals(ADD_PERSON_COMMAND, commandHistory.getPreviousCommand());
        assertEquals(ADD_PERSON_COMMAND, commandHistory.getNextCommand());
    }

    @Test
    public void addCommand_multipleCommands_updatesCommandHistory() {
        for (String commandText : LIST_OF_COMMANDS) {
            commandHistory.addCommand(commandText);
        }
        // check commands have been added
        assertTrue(commandHistory.hasCommands());
    }

    @Test
    public void hasPrevious_noCommandHistory_returnFalse() {
        assertFalse(commandHistory.hasPrevious());
    }

    @Test
    public void hasPrevious_hasCommandHistory_returnTrue() {
        for (String commandText : LIST_OF_COMMANDS) {
            commandHistory.addCommand(commandText);
        }
        assertTrue(commandHistory.hasPrevious());
    }

    @Test
    public void hasNext_noCommandHistory_returnFalse() {
        assertFalse(commandHistory.hasNext());
    }

    @Test
    public void hasNext_hasCommandHistory_returnTrue() {
        for (String commandText : LIST_OF_COMMANDS) {
            commandHistory.addCommand(commandText);
        }
        String previousCommand = commandHistory.getPreviousCommand();
        assertTrue(commandHistory.hasNext());
    }

    @Test
    public void getPreviousCommand_singleCommand_returnCurrentCommand() {
        commandHistory.addCommand(ADD_PERSON_COMMAND);
        assertEquals(ADD_PERSON_COMMAND, commandHistory.getPreviousCommand());
    }

    @Test
    public void getPreviousCommand_multipleCommands_returnCorrespondingCommand() {
        for (String commandText : LIST_OF_COMMANDS) {
            commandHistory.addCommand(commandText);
        }
        assertEquals(DELETE_PERSON_COMMAND, commandHistory.getPreviousCommand());
        assertEquals(ADD_PERSON_COMMAND, commandHistory.getPreviousCommand());
    }

    @Test
    public void getNextCommand_singleCommand_returnCurrentCommand() {
        commandHistory.addCommand(ADD_PERSON_COMMAND);
        assertEquals(ADD_PERSON_COMMAND, commandHistory.getNextCommand());
    }

    @Test
    public void getNextCommand_multipleCommands_returnCorrespondingCommand() {
        for (String commandText : LIST_OF_COMMANDS) {
            commandHistory.addCommand(commandText);
        }
        assertEquals(DELETE_PERSON_COMMAND, commandHistory.getPreviousCommand());
        assertEquals(ADD_PERSON_COMMAND, commandHistory.getPreviousCommand());
        assertEquals(DELETE_PERSON_COMMAND, commandHistory.getNextCommand());
        assertEquals(LIST_COMMAND, commandHistory.getNextCommand());
    }

    @Test
    public void getCommandHistory_singleCommand_returnCommandHistory() {
        commandHistory.addCommand(ADD_PERSON_COMMAND);
        assertEquals(ONE_COMMAND_COMMAND_HISTORY, commandHistory.getCommandHistory());
    }

    @Test
    public void getCommandHistory_multipleCommand_returnCommandHistory() {
        for (String commandText : LIST_OF_COMMANDS) {
            commandHistory.addCommand(commandText);
        }
        assertEquals(THREE_COMMANDS_COMMAND_HISTORY, commandHistory.getCommandHistory());
    }
}
