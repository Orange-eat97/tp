package seedu.address.ui;

import java.util.List;

import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.TextField;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.SortCommand;



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
        int caret = commandTextField.getCaretPosition();
        boolean isAddSort = text.contains("add") || text.contains("sort");
        boolean isCaretPostionForParams = caret > 0 && text.charAt(caret - 1) == ' ';

        if (isCaretPostionForParams && isAddSort) { //case of having a space after add or sort

            assert (text.contains("add") || text.contains("sort"));

            String next = null;

            if (text.contains("add")) {
                next = autoCompleteSupplier.giveParam(text, getParamList("add")); //get the suggested next param
            } else if (text.contains("sort")) {
                next = autoCompleteSupplier.giveParam(text, getParamList("sort"));
            }

            if (next != null && !next.isEmpty()) { //possible to get null as a next, when all params have been typed
                acLastSuggestion = next;
                acTokenStart = caret;
                updateLabelAndShow(next, acGhostItem, commandTextField);
                return null;
            }

        } else if (isCaretPostionForParams && text.contains("edit")) {
            String next = null;
            if (!text.matches(".*\\d.*")) { //checks if the text field has a number, if no, do not suggest first
                acGhostHide();
            } else {
                next = autoCompleteSupplier.giveParam(text, getParamList("edit"));
                acLastSuggestion = next;
                acTokenStart = caret;
                updateLabelAndShow(next, acGhostItem, commandTextField);
                return null;
            }

        } else if (!text.contains(" ")) { //case of suggesting for command: assertion text has no " "
            if (start >= end) { // empty token
                acGhostHide();
                return null;
            }

            String prefix = text.substring(start, end);

            String suggestion = null;
            if (start != end) { //added guarding rail so space at the end won't suggest "add"
                suggestion = autoCompleteSupplier.findBestMatch(prefix);
            }

            if (suggestion == null || suggestion.isEmpty() || suggestion.equals(prefix)) {
                acGhostHide();
                return null;
            }

            acLastSuggestion = suggestion;
            acTokenStart = start;
            String tail = suggestion.substring(prefix.length());
            updateLabelAndShow(tail, acGhostItem, commandTextField);
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
     * abstraction for getting paramList
     * @param command command word
     * @return param list of each command class
     */
    private List<String> getParamList(String command) {
        switch (command) {
        case "add": return AddCommand.PARAMS;
        case "sort": return SortCommand.PARAMS;
        case "edit": return EditCommand.PARAMS;
        default: return null;
        }
    }

    /**
     * abstraction for updating javaFx label
     * @param next string to be displayed
     * @param acGhostItem current label item
     */
    private void updateJavaFxLable(String next, CustomMenuItem acGhostItem) {
        javafx.scene.control.Label label = (javafx.scene.control.Label) acGhostItem.getContent();
        label.setText(next);
    }

    private void updateLabelAndShow(String next, CustomMenuItem acGhostItem, TextField commandTextField) {
        updateJavaFxLable(next, acGhostItem);
        ghostShow(commandTextField);
    }

}
