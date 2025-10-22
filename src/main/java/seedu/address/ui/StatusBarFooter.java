package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;

    @FXML
    private Label sortStatus;

    @FXML
    private Label findStatus;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public StatusBarFooter(Path saveLocation) {
        super(FXML);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
    }

    public void setSortStatus(String sortStatusText) {
        if (sortStatusText == null) return;
        sortStatus.setText(sortStatusText);
        sortStatus.setTooltip(new Tooltip(sortStatusText));
    }

    public void setFindStatus(String findStatusText) {
        if (findStatusText == null) return;
        findStatus.setText(findStatusText);
        findStatus.setTooltip(new Tooltip(findStatusText));
    }
}
