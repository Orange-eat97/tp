package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.KeywordMatch;
import seedu.address.testutil.PersonBuilder;

public class StrAttrContainsKeywordsTest {

    @Test
    public void equals() {
        Set<KeywordMatch> firstPredicateKeywordSet = Set.of(new KeywordMatch("first", false));
        Set<KeywordMatch> secondPredicateKeywordSet = Set.of(
                new KeywordMatch("first", false),
                new KeywordMatch("second", false));

        StrAttrContainsKeywords firstPredicate = new StrAttrContainsKeywords(
                firstPredicateKeywordSet, Person.NAME_STR_GETTER);
        StrAttrContainsKeywords secondPredicate = new StrAttrContainsKeywords(
                secondPredicateKeywordSet, Person.PHONE_STR_GETTER);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StrAttrContainsKeywords firstPredicateCopy = new StrAttrContainsKeywords(
                new HashSet<>(firstPredicateKeywordSet),
                Person.NAME_STR_GETTER
        );
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        StrAttrContainsKeywords predicate = new StrAttrContainsKeywords(
                Set.of(new KeywordMatch("Alice", false)), Person.NAME_STR_GETTER);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new StrAttrContainsKeywords(new HashSet<>(
                Set.of(new KeywordMatch("Alice", false),
                        new KeywordMatch("Bob", false))),
                Person.NAME_STR_GETTER);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new StrAttrContainsKeywords(
                Set.of(new KeywordMatch("Bob", false),
                    new KeywordMatch("Carol", false)), Person.NAME_STR_GETTER);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));
        // Mixed-case keywords
        predicate = new StrAttrContainsKeywords(
                Set.of(new KeywordMatch("aLIce", false),
                    new KeywordMatch("bOB", false)), Person.NAME_STR_GETTER);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        StrAttrContainsKeywords predicate =
                new StrAttrContainsKeywords(new HashSet<>(Collections.emptyList()), Person.NAME_STR_GETTER);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new StrAttrContainsKeywords(
                Set.of(new KeywordMatch("Carol", false)), Person.NAME_STR_GETTER);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        Set<KeywordMatch> keywordMatches = Set.of(
                new KeywordMatch("12345", false),
                new KeywordMatch("alice@email.com", false),
                new KeywordMatch("Main", false),
                new KeywordMatch("Street", false)
        );
        predicate = new StrAttrContainsKeywords(keywordMatches, Person.NAME_STR_GETTER);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        Set<KeywordMatch> keywordMatches = Set.of(
                new KeywordMatch("keyword1", false),
                new KeywordMatch("keyword2", false));
        StrAttrContainsKeywords predicate =
                new StrAttrContainsKeywords(keywordMatches, Person.NAME_STR_GETTER);

        String expected = StrAttrContainsKeywords.class.getCanonicalName() + "{keywords=" + keywordMatches + "}";
        assertEquals(expected, predicate.toString());
    }
}
