package seedu.address.ui;

import java.util.function.Supplier;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private final CommandExecutor commandExecutor;
    private final Ghost ghost = new Ghost();

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor,
                      Supplier<String> getPreviousCommand,
                      Supplier<String> getNextCommand) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3)
                -> setStyleToDefault());

        commandTextField.textProperty().addListener((obs, oldV, newV) -> {
            if (ghost.getEditStatus()) {
                return;
            }
            if (newV != null && oldV != null && newV.length() < oldV.length()) {
                ghost.acGhostHide();
                return;
            }
            javafx.application.Platform.runLater(() -> ghost.acRefreshGhostPreview(commandTextField));
        });

        commandTextField.focusedProperty().addListener((obs, was, focused)
                -> {
            if (!focused && ghost.acHasGhost(commandTextField)) {
                int end = ghost.acClearGhost(commandTextField);
                commandTextField.selectRange(end, end);
            }
        });

        commandTextField.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            switch (e.getCode()) {
            case TAB -> {
                if (ghost.getLastSuggestion() != null) {
                    ghost.acCommitSuggestion(commandTextField);
                }
                e.consume();
            }
            default -> { }
            }
        });

    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the given text in the command box and moves the caret to the end.
     *
     * @param commandText the text to display in the command box
     */
    public void setCommandText(String commandText) {
        commandTextField.setText(commandText);
        commandTextField.positionCaret(commandText.length());
    }


    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}
