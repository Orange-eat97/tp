package seedu.address.ui;

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
    private boolean acInternalEdit = false;
    private String acLastSuggestion = null;
    private int acTokenStart = 0;

    private final javafx.scene.control.ContextMenu acGhost = new javafx.scene.control.ContextMenu();
    private final javafx.scene.control.CustomMenuItem acGhostItem =
            new javafx.scene.control.CustomMenuItem(new javafx.scene.control.Label(), false);

    private final CommandExecutor commandExecutor;
    private final AutoCompleteSupplier autoCompleteSupplier = new AutoCompleteSupplier();

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        acGhost.getItems().setAll(acGhostItem);
        acGhost.setAutoHide(true);
        acGhost.setAutoFix(true);
        acGhost.setConsumeAutoHidingEvents(false);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        commandTextField.textProperty().addListener((obs, oldV, newV) -> {
            if (acInternalEdit) {
                return;
            }
            javafx.application.Platform.runLater(this::acRefreshGhostPreview); // get new preview of ghost
        });

        commandTextField.focusedProperty().addListener((obs, was, focused) -> {
            if (!focused) {
                acClearGhost();
            }
        });

        commandTextField.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            switch (e.getCode()) {
            case TAB -> {
                if (acLastSuggestion != null) {
                    acCommitSuggestion();
                    e.consume();
                }
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

    /**
    * A helper function that reads the starting and ending position of the text input. Returns a length-2 array
     * containining the position of start and end of the word being tpyed
    * **/
    private int[] acCurrentTokenBounds() {
        String t = commandTextField.getText();
        if (t == null) {
            t = "";
        }
        int caret = commandTextField.getCaretPosition();
        if (caret < 0) {
            caret = 0;
        }
        if (caret > t.length()) {
            caret = t.length();
        }
        if (t.isEmpty() || caret == 0) {
            return new int[]{caret, caret};
        }

        int i = caret - 1;
        while (i >= 0) {
            char c = t.charAt(i);
            if (Character.isLetterOrDigit(c) || c == '_' || c == ':' || c == '-') {
                i--;
            } else {
                break;
            }
        }
        int start = i + 1;
        return new int[]{start, caret};
    }

    /**
     * function that is called upon tab click event, replaces the word with the ghost tail and moves caret
     * to the end of the word
     * **/
    private void acCommitSuggestion() {
        if (acLastSuggestion == null) {
            return; //base case for default state
        }

        int caret = commandTextField.getCaretPosition(); //assertion: should be at the end of the word

        acInternalEdit = true; //enable editing
        try {
            commandTextField.replaceText(acTokenStart, caret, acLastSuggestion);
            int newCaret = acTokenStart + acLastSuggestion.length();
            commandTextField.positionCaret(newCaret);
            commandTextField.deselect();
        } finally {
            acInternalEdit = false;
        }
        acGhostHide();
    }

    /**
     * handles events such as other key pressed
     * **/
    private void acClearGhost() {
        if (!acHasGhost()) {
            return;
        }
        acInternalEdit = true;
        try {
            int end = Math.max(commandTextField.getAnchor(), commandTextField.getCaretPosition());
            commandTextField.selectRange(end, end); // collapse selection; keeps current text
        } finally {
            acInternalEdit = false;
        }
    }

    /**
     * state check is there any suggestion
     * **/
    private boolean acHasGhost() {
        return commandTextField.getSelection().getLength() > 0
                && commandTextField.getAnchor() != commandTextField.getCaretPosition();
    }

    /**
     * updates state as user types, updates acTokenStart and acLastSuggestion for computing the tail,
     * updates label in ghostItem which is the display for tail
     * **/
    private void acRefreshGhostPreview() {
        String text = commandTextField.getText();
        if (text == null) {
            text = "";
        }

        int[] bounds = acCurrentTokenBounds();
        int start = bounds[0];
        int end = bounds[1];

        if (start >= end) { // empty token
            acGhostHide();
            return;
        }

        String prefix = text.substring(start, end);

        String suggestion = autoCompleteSupplier.findBestMatch(prefix);
        if (suggestion == null || suggestion.isEmpty() || suggestion.equals(prefix)) {
            acGhostHide();
            return;
        }

        acLastSuggestion = suggestion;
        acTokenStart = start;

        String tail = suggestion.substring(prefix.length());

        javafx.scene.control.Label label = (javafx.scene.control.Label) acGhostItem.getContent();
        label.setText(tail);

        if (!acGhost.isShowing()) {
            javafx.geometry.Bounds b = commandTextField.localToScreen(commandTextField.getBoundsInLocal());
            if (b != null) {
                acGhost.show(commandTextField, b.getMinX(), b.getMaxY());
            } else {
                acGhost.show(commandTextField, javafx.geometry.Side.BOTTOM, 0, 0);
            }
        } else {
            acGhost.hide();
            javafx.geometry.Bounds b = commandTextField.localToScreen(commandTextField.getBoundsInLocal());
            if (b != null) {
                acGhost.show(commandTextField, b.getMinX(), b.getMaxY());
            }
        }
    }

    /**
     * hides the drop-down window
     * **/
    private void acGhostHide() {
        acLastSuggestion = null;
        if (acGhost.isShowing()) {
            acGhost.hide();
        }
    }

}
