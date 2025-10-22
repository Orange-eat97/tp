package seedu.address.ui;

import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        name.setTooltip(new Tooltip(person.getName().fullName));

        phone.setText(person.getPhone().value);
        phone.setTooltip(new Tooltip(person.getPhone().value));

        address.setText(person.getAddress().value);
        address.setTooltip(new Tooltip(person.getAddress().value));

        email.setText(person.getEmail().value);
        email.setTooltip(new Tooltip(person.getEmail().value));

        List<String> priorityOrder = List.of("beneficiary", "volunteer");

        Comparator<Tag> tagComparator = Comparator.comparingInt((Tag tag) -> {
            String name = tag.tagName.toLowerCase().strip();
            int index = priorityOrder.indexOf(name);
            return index == -1 ? Integer.MAX_VALUE : index;
        }).thenComparing(tag -> tag.tagName.toLowerCase());

        person.getTags().stream()
            .sorted(tagComparator)
            .forEach(tag -> {
                Label tagLabel = new Label(tag.tagName);
                tagLabel.getStyleClass().add("tag-label"); // base style
                switch (tag.tagName.toLowerCase().strip()) {
                case "volunteer":
                    tagLabel.getStyleClass().add("tag-volunteer");
                    break;
                case "beneficiary":
                    tagLabel.getStyleClass().add("tag-beneficiary");
                    break;
                default:
                    tagLabel.getStyleClass().add("tag-default");
                    break;
                }
                tags.getChildren().add(tagLabel);
            });
    }
}
