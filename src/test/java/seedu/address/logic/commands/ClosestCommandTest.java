package seedu.address.logic.commands;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import static seedu.address.logic.commands.ClosestCommand.PREDICATE_SHOW_ALL_BENEFICIARY;
import static seedu.address.logic.commands.ClosestCommand.PREDICATE_SHOW_ALL_VOLUNTEERS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Region;
import seedu.address.model.tag.Tag;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class ClosestCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_correctOrdering_success() {
        Index targetIndex = Index.fromZeroBased(3);
        Region targetRegion = DANIEL.getRegion();
        String expectedMessage = String.format("Sorted and filtered to find closest beneficiary to %s",
                targetRegion.value.getDisplayName());

        ClosestCommand command = new ClosestCommand(targetIndex);
        Comparator<Person> personComparator = ClosestCommand.createClosestComparator(DANIEL);
        expectedModel.updateDisplayList(personComparator);
        expectedModel.updateDisplayList(PREDICATE_SHOW_ALL_BENEFICIARY);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        for (int i = 0; i < model.getDisplayList().size() - 1; i++) {
            Region r1 = model.getDisplayList().get(i).getRegion();
            Region r2 = model.getDisplayList().get(i + 1).getRegion();

            int d1 = targetRegion.getDistanceFrom(r1);
            int d2 = targetRegion.getDistanceFrom(r2);

            assertTrue(d1 <= d2, "%s compared to %s, %d vs %d".formatted(r1.value.getDisplayName(),
                    r2.value.getDisplayName(), d1, d2));
        }
    }

    @Test
    public void execute_ifVolunteerFilterBeneficiary_success() {
        Index targetIndex = Index.fromZeroBased(3);
        Region targetRegion = DANIEL.getRegion();
        String expectedMessage = String.format("Sorted and filtered to find closest beneficiary to %s",
                targetRegion.value.getDisplayName());

        ClosestCommand command = new ClosestCommand(targetIndex);
        Comparator<Person> personComparator = ClosestCommand.createClosestComparator(DANIEL);
        expectedModel.updateDisplayList(personComparator);
        expectedModel.updateDisplayList(PREDICATE_SHOW_ALL_BENEFICIARY);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        for (int i = 0; i < model.getDisplayList().size() - 1; i++) {
            boolean isBeneficiary = model.getDisplayList().get(i).getTags().contains(new Tag("beneficiary"));

            assertTrue(isBeneficiary, "%s is not a beneficiary".formatted(model.getDisplayList().get(i).getName().fullName));
        }
    }

    @Test
    public void execute_ifBeneficiaryFilterVolunteer_success() {
        Index targetIndex = Index.fromZeroBased(5);
        Region targetRegion = FIONA.getRegion();
        String expectedMessage = String.format("Sorted and filtered to find closest volunteer to %s",
                targetRegion.value.getDisplayName());

        ClosestCommand command = new ClosestCommand(targetIndex);
        Comparator<Person> personComparator = ClosestCommand.createClosestComparator(FIONA);
        expectedModel.updateDisplayList(personComparator);
        expectedModel.updateDisplayList(PREDICATE_SHOW_ALL_VOLUNTEERS);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        for (int i = 0; i < model.getDisplayList().size() - 1; i++) {
            boolean isVolunteer = model.getDisplayList().get(i).getTags().contains(new Tag("volunteer"));

            assertTrue(isVolunteer, "%s is not a volunteer".formatted(model.getDisplayList().get(i).getName().fullName));
        }
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getDisplayList().size() + 1);
        ClosestCommand command = new ClosestCommand(outOfBoundsIndex);

        try {
            command.execute(model);
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, e.getMessage());
        }
    }
}
