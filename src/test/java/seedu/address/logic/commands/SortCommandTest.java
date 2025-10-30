package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.NaturalOrderComparator.NATURAL_ORDER_COMPARATOR;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.Prefix;
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
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "\n• name\n");
        List<Prefix> prefixList = new ArrayList<>();
        prefixList.add(PREFIX_NAME);
        SortCommand command = new SortCommand(nameComparator, prefixList);
        expectedModel.updateDisplayList(nameComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getDisplayList());

    }

    @Test
    public void execute_sortListByEmail_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "\n• email\n");
        List<Prefix> prefixList = new ArrayList<>();
        prefixList.add(PREFIX_EMAIL);
        SortCommand command = new SortCommand(emailComparator, prefixList);
        expectedModel.updateDisplayList(emailComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, GEORGE, DANIEL, CARL, BENSON, FIONA, ELLE), model.getDisplayList());

    }

    @Test
    public void execute_sortListByAddress_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "\n• address\n");
        List<Prefix> prefixList = new ArrayList<>();
        prefixList.add(PREFIX_ADDRESS);
        SortCommand command = new SortCommand(addressComparator, prefixList);
        expectedModel.updateDisplayList(addressComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GEORGE, DANIEL, ALICE, BENSON, FIONA, ELLE, CARL), model.getDisplayList());

    }

    @Test
    public void execute_sortListByTag_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "\n• volunteer/beneficiary tags\n");
        List<Prefix> prefixList = new ArrayList<>();
        prefixList.add(PREFIX_TAG);
        SortCommand command = new SortCommand(tagComparator, prefixList);
        expectedModel.updateDisplayList(tagComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, FIONA, GEORGE, ALICE, BENSON, CARL, DANIEL), model.getDisplayList());

    }

    @Test
    public void execute_sortListByPhone_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "\n• phone number\n");
        List<Prefix> prefixList = new ArrayList<>();
        prefixList.add(PREFIX_PHONE);
        SortCommand command = new SortCommand(phoneComparator, prefixList);
        expectedModel.updateDisplayList(phoneComparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, FIONA, GEORGE, DANIEL, ALICE, CARL, BENSON), model.getDisplayList());

    }

    @Test
    public void execute_sortMultipleInput_success() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "\n• name\n• phone number\n• volunteer/beneficiary tags\n");
        List<Prefix> prefixList = new ArrayList<>();
        prefixList.add(PREFIX_NAME);
        prefixList.add(PREFIX_PHONE);
        prefixList.add(PREFIX_TAG);
        Comparator<Person> comparator = nameComparator.thenComparing(phoneComparator)
                .thenComparing(tagComparator);
        SortCommand command = new SortCommand(comparator, prefixList);
        expectedModel.updateDisplayList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getDisplayList());

    }


    private Comparator<Person> prepareComparator(Function<Person, String> strGetter) {
        return Comparator.comparing(strGetter, NATURAL_ORDER_COMPARATOR);

    }

}
