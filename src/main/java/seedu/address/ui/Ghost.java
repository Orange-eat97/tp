package seedu.address.ui;

import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.TextField;
import seedu.address.logic.parser.AutoCompleteParser;


/**
 * class responsible for building the ghost of auto-complete
 */
public class Ghost {
    private boolean acInternalEdit = false;
    private String acLastSuggestion = null;
    private int acTokenStart = 0;

    private final javafx.scene.control.ContextMenu acGhost = new javafx.scene.control.ContextMenu();
    private final javafx.scene.control.CustomMenuItem acGhostItem =
            new javafx.scene.control.CustomMenuItem(new javafx.scene.control.Label(), false);
    private final AutoCompleteSupplier autoCompleteSupplier = new AutoCompleteSupplier();

    /**
     * constructor for ghost
     */
    public Ghost() {
        acLastSuggestion = null;
        acTokenStart = 0;
        acGhost.getItems().setAll(acGhostItem);
        acGhost.setAutoHide(true);
        acGhost.setAutoFix(true);
        acGhost.setConsumeAutoHidingEvents(false);
    }

    public boolean getEditStatus() {
        return this.acInternalEdit;
    }

    public String getLastSuggestion() {
        return this.acLastSuggestion;
    }


    /**
     * only gets called when acHasGhost is true. handles situation when other keys are pressed
     * @param commandTextField
     * @return int end position
     */
    public int acClearGhost(TextField commandTextField) {
        acInternalEdit = true;
        int end = Math.max(commandTextField.getAnchor(), commandTextField.getCaretPosition());
        acInternalEdit = false;
        return end;
    }

    /**
     * updates state as user types, updates acTokenStart and acLastSuggestion for computing the tail,
     * updates label in ghostItem which is the display for tail
     * @param commandTextField Textfield of the current state
     * @return
     */
    public Runnable acRefreshGhostPreview(TextField commandTextField) {
        String text = commandTextField.getText();
        int caret = commandTextField.getCaretPosition();

        String[] commands = AutoCompleteParser.command(text, caret);
        if (commands[0] == AutoCompleteParser.HIDE_COMMAND) {
            acGhostHide();
            return null;
        } else if (commands[0] == AutoCompleteParser.SHOW_COMMAND) {
            int start = Integer.parseInt(commands[2]);
            updateState(commands[1], start);
            updateLabelAndShow(commands[3], acGhostItem, commandTextField);
            return null;
        }
        return null;
    }

    /**
     * logic for showing the suggestion, abstracted out
     * @param commandTextField
     */
    private void ghostShow(TextField commandTextField) {
        if (!acGhost.isShowing()) {
            javafx.geometry.Bounds bounds = commandTextField.localToScreen(commandTextField.getBoundsInLocal());
            if (bounds != null) {
                acGhost.show(commandTextField, bounds.getMinX(), bounds.getMaxY());
            } else {
                acGhost.show(commandTextField, javafx.geometry.Side.BOTTOM, 0, 0);
            }
        } else {
            acGhost.hide();
            javafx.geometry.Bounds bounds = commandTextField.localToScreen(commandTextField.getBoundsInLocal());
            if (bounds != null) {
                acGhost.show(commandTextField, bounds.getMinX(), bounds.getMaxY());
            }
        }
    }

    /**
     * modifies the input commandTextfield;
     * @param commandTextField
     */
    public void acCommitSuggestion(TextField commandTextField) {
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
     * status check of ghost
     * @param commandTextField
     * @return
     */
    public boolean acHasGhost(TextField commandTextField) {
        return commandTextField.getSelection().getLength() > 0
                && commandTextField.getAnchor() != commandTextField.getCaretPosition();
    }

    /**
     * hides the drop-down window
     */
    public void acGhostHide() {
        acLastSuggestion = null;
        if (acGhost.isShowing()) {
            acGhost.hide();
        }
    }


    /**
     * abstraction of updating label and showing
     * @param next string to be shown
     * @param acGhostItem dropdown menu
     * @param commandTextField
     */
    private void updateLabelAndShow(String next, CustomMenuItem acGhostItem, TextField commandTextField) {
        javafx.scene.control.Label label = (javafx.scene.control.Label) acGhostItem.getContent();
        label.setText(next);
        ghostShow(commandTextField);
    }


    /**
     * abstraction for updating state of ghost
     * @param suggestion
     * @param start
     */
    private void updateState(String suggestion, int start) {
        acLastSuggestion = suggestion;
        acTokenStart = start;
    }
}
