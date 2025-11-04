package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.FindCommand.FIND_RESET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.KeywordMatch;
import seedu.address.logic.parser.Prefix;
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
        Set<KeywordMatch> firstKeywordSet = new HashSet<>(List.of(new KeywordMatch("first", false)));
        Set<KeywordMatch> secondKeywordSet = new HashSet<>(List.of(new KeywordMatch("second", false)));
        StrAttrContainsKeywords firstPredicate = new StrAttrContainsKeywords(firstKeywordSet, Person.NAME_STR_GETTER);
        StrAttrContainsKeywords secondPredicate = new StrAttrContainsKeywords(secondKeywordSet, Person.NAME_STR_GETTER);

        FindCommand findFirstCommand = new FindCommand(firstPredicate, Map.of(PREFIX_NAME, firstKeywordSet));
        FindCommand findSecondCommand = new FindCommand(secondPredicate, Map.of(PREFIX_NAME, secondKeywordSet));

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate, Map.of(PREFIX_NAME, firstKeywordSet));
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
        String expectedMessage = String.format(FindCommand.FIND_SUCCESS_OVERVIEW, 0) + FIND_RESET;
        StrAttrContainsKeywords predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate, new HashMap<>());
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleKeyword_onePersonFound() {
        // keyword is full word
        Map<Prefix, Set<KeywordMatch>> noPrefixMap = Map.of(
                PREFIX_NAME, Set.of(new KeywordMatch("Elle", false))
        );
        String expectedMessage = String.format(FindCommand.FIND_SUCCESS_OVERVIEW, 1) + prepareDescription(noPrefixMap);
        StrAttrContainsKeywords predicate = preparePredicate("Elle");
        FindCommand command = new FindCommand(predicate, noPrefixMap);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE), model.getFilteredPersonList());

        // keyword is prefix of word
        Map<Prefix, Set<KeywordMatch>> prefixMap = Map.of(
                PREFIX_NAME, Set.of(new KeywordMatch("El", true))
        );
        expectedMessage = String.format(FindCommand.FIND_SUCCESS_OVERVIEW, 1) + prepareDescription(prefixMap);
        predicate = preparePredicate("El%");
        command = new FindCommand(predicate, prefixMap);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        Map<Prefix, Set<KeywordMatch>> keywordMap = Map.of(
                PREFIX_NAME, Set.of(new KeywordMatch("Kurz", false),
                        new KeywordMatch("Elle", false),
                        new KeywordMatch("Kunz", false))
        );
        String expectedMessage = String.format(FindCommand.FIND_SUCCESS_OVERVIEW, 3) + prepareDescription(keywordMap);
        StrAttrContainsKeywords predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate, keywordMap);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        Set<KeywordMatch> keywordMatches = new HashSet<>(List.of(new KeywordMatch("keyword", false)));
        StrAttrContainsKeywords predicate = new StrAttrContainsKeywords(keywordMatches, Person.NAME_STR_GETTER);
        FindCommand findCommand = new FindCommand(predicate, Map.of(PREFIX_NAME, keywordMatches));
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code StrAttrContainsKeywords} predicate with word and prefix match.
     */
    private StrAttrContainsKeywords preparePredicate(String userInput) {
        Set<KeywordMatch> keywordMatches = Arrays.stream(userInput.split("\\s+"))
            .map(keyword -> {
                if (keyword.endsWith("%")) {
                    return new KeywordMatch(keyword.substring(0, keyword.length() - 1), true);
                } else {
                    return new KeywordMatch(keyword, false);
                }
            })
            .collect(Collectors.toSet());
        return new StrAttrContainsKeywords(keywordMatches, Person.NAME_STR_GETTER);
    }

    /**
     * Prepares description string from {@code Map<Prefix, Set<KeywordMatch>>}.
     */
    private String prepareDescription(Map<Prefix, Set<KeywordMatch>> prefixMatches) {
        Map<Prefix, String> prefixLabels = Map.of(
                PREFIX_NAME, "name",
                PREFIX_ADDRESS, "address",
                PREFIX_PHONE, "phone number",
                PREFIX_REGION, "region",
                PREFIX_EMAIL, "email",
                PREFIX_TAG, "tag"
        );
        if (prefixMatches.isEmpty()) {
            return "";
        }
        return "• " + prefixMatches.entrySet().stream()
                .map(
                        entry -> {
                            String keywords = entry.getValue().stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(" "));
                            return prefixLabels.get(entry.getKey()) + ": " + keywords;
                        })
                .collect(Collectors.joining("\n• ")) + FIND_RESET;
    }
}
