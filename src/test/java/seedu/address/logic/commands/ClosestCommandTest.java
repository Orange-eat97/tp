package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Region;

public class ClosestCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_selectedPersonFirst_success() {
        Index targetIndex = Index.fromZeroBased(0);
        Region targetRegion = ALICE.getRegion();
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS.formatted("closest volunteer to %s"),
                targetRegion.value.getDisplayName());

        ClosestCommand command = new ClosestCommand(targetIndex);
        expectedModel.updateDisplayList(ClosestCommand.createClosestComparator(ALICE));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(ALICE, model.getDisplayList().get(0));
    }

    @Test
    public void execute_correctOrdering_success() {
        Index targetIndex = Index.fromZeroBased(3);
        Region targetRegion = DANIEL.getRegion();
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS.formatted("closest volunteer to %s"),
                targetRegion.value.getDisplayName());

        ClosestCommand command = new ClosestCommand(targetIndex);
        Comparator<Person> personComparator = ClosestCommand.createClosestComparator(DANIEL);
        expectedModel.updateDisplayList(personComparator);

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
