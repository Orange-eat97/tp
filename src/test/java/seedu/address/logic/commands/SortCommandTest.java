package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.NaturalOrderComparator.NATURAL_ORDER_COMPARATOR;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final Comparator<Person> nameComparator = prepareComparator(Person.NAME_STR_GETTER);
    private final Comparator<Person> emailComparator = prepareComparator(Person.EMAIL_STR_GETTER);
    private final Comparator<Person> addressComparator = prepareComparator(Person.ADDRESS_STR_GETTER);
    private final Comparator<Person> phoneComparator = prepareComparator(Person.PHONE_STR_GETTER);
    private final Comparator<Person> tagComparator = prepareComparator(Person.ROLE_TAG_STR_GETTER);

    @Test
    public void execute_sortListByName_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name");
        SortCommand command = new SortCommand(nameComparator, "name");
        expectedModel.updateDisplayList(nameComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getDisplayList());

    }

    @Test
    public void execute_sortListByEmail_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "email");
        SortCommand command = new SortCommand(emailComparator, "email");
        expectedModel.updateDisplayList(emailComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, GEORGE, DANIEL, CARL, BENSON, FIONA, ELLE), model.getDisplayList());

    }

    @Test
    public void execute_sortListByAddress_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "address");
        SortCommand command = new SortCommand(addressComparator, "address");
        expectedModel.updateDisplayList(addressComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GEORGE, DANIEL, ALICE, BENSON, FIONA, ELLE, CARL), model.getDisplayList());

    }

    @Test
    public void execute_sortListByTag_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "tag");
        SortCommand command = new SortCommand(tagComparator, "tag");
        expectedModel.updateDisplayList(tagComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, FIONA, GEORGE, ALICE, BENSON, CARL, DANIEL), model.getDisplayList());

    }

    @Test
    public void execute_sortListByPhone_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "phone");
        SortCommand command = new SortCommand(phoneComparator, "phone");
        expectedModel.updateDisplayList(phoneComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, FIONA, GEORGE, DANIEL, ALICE, CARL, BENSON), model.getDisplayList());

    }

    @Test
    public void execute_sortMultipleInput_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name, phone, tag");
        Comparator<Person> comparator = nameComparator.thenComparing(phoneComparator)
                .thenComparing(tagComparator);
        SortCommand command = new SortCommand(comparator, "name, phone, tag");
        expectedModel.updateDisplayList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getDisplayList());

    }


    private Comparator<Person> prepareComparator(Function<Person, String> strGetter) {
        return Comparator.comparing(strGetter, NATURAL_ORDER_COMPARATOR);

    }

}
