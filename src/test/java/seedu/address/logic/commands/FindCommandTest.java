package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.KeywordMatch;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.StrAttrContainsKeywords;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        StrAttrContainsKeywords firstPredicate = new StrAttrContainsKeywords(
                new HashSet<>(List.of(new KeywordMatch("first", false))), Person.NAME_STR_GETTER);
        StrAttrContainsKeywords secondPredicate = new StrAttrContainsKeywords(
                new HashSet<>(List.of(new KeywordMatch("second", false))), Person.NAME_STR_GETTER);

        FindCommand findFirstCommand = new FindCommand(firstPredicate, "", "");
        FindCommand findSecondCommand = new FindCommand(secondPredicate, "", "");

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate, "", "");
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different keywords -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(FindCommand.FIND_SUCCESS_OVERVIEW, 0);
        StrAttrContainsKeywords predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate, "", "");
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(FindCommand.FIND_SUCCESS_OVERVIEW, 3);
        StrAttrContainsKeywords predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate, "", "");
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        StrAttrContainsKeywords predicate = new StrAttrContainsKeywords(
                new HashSet<>(List.of(new KeywordMatch("keyword", false))), Person.NAME_STR_GETTER);
        FindCommand findCommand = new FindCommand(predicate, "", "");
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code StrAttrContainsKeywords} predicate with only word match.
     */
    private StrAttrContainsKeywords preparePredicate(String userInput) {
        Set<KeywordMatch> keywordMatches = Arrays.stream(userInput.split("\\s+"))
            .map(keyword -> new KeywordMatch(keyword, false))
            .collect(Collectors.toSet());
        return new StrAttrContainsKeywords(keywordMatches, Person.NAME_STR_GETTER);
    }
}
