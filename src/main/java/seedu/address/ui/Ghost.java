package seedu.address.ui;

import javafx.scene.control.TextField;

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
     * A helper function that reads the starting and ending position of the text input. Returns a length-2 array
     * containining the position of start and end of the word being tpyed
     * @param
     */
    public int[] acCurrentTokenBounds(TextField commandTextField) {
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
        if (text == null) {
            text = "";
        }

        int[] bounds = acCurrentTokenBounds(commandTextField);
        int start = bounds[0];
        int end = bounds[1];

        if (start >= end) { // empty token
            acGhostHide();
            return null;
        }

        String prefix = text.substring(start, end);

        String suggestion = autoCompleteSupplier.findBestMatch(prefix);
        if (suggestion == null || suggestion.isEmpty() || suggestion.equals(prefix)) {
            acGhostHide();
            return null;
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
        return null;
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

}
