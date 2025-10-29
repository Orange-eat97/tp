package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class StrAttrContainsKeywordsTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        StrAttrContainsKeywords firstPredicate =
                new StrAttrContainsKeywords(new HashSet<>(firstPredicateKeywordList), Person.NAME_STR_GETTER);
        StrAttrContainsKeywords secondPredicate =
                new StrAttrContainsKeywords(new HashSet<>(secondPredicateKeywordList), Person.PHONE_STR_GETTER);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StrAttrContainsKeywords firstPredicateCopy = new StrAttrContainsKeywords(
                new HashSet<>(firstPredicateKeywordList),
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
        StrAttrContainsKeywords predicate =
                new StrAttrContainsKeywords(new HashSet<>(Collections.singletonList("Alice")), Person.NAME_STR_GETTER);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new StrAttrContainsKeywords(new HashSet<>(Arrays.asList("Alice", "Bob")), Person.NAME_STR_GETTER);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new StrAttrContainsKeywords(new HashSet<>(Arrays.asList("Bob", "Carol")), Person.NAME_STR_GETTER);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new StrAttrContainsKeywords(new HashSet<>(Arrays.asList("aLIce", "bOB")), Person.NAME_STR_GETTER);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        StrAttrContainsKeywords predicate =
                new StrAttrContainsKeywords(new HashSet<>(Collections.emptyList()), Person.NAME_STR_GETTER);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new StrAttrContainsKeywords(new HashSet<>(Arrays.asList("Carol")), Person.NAME_STR_GETTER);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new StrAttrContainsKeywords(
                new HashSet<>(Arrays.asList("12345", "alice@email.com", "Main", "Street")), Person.NAME_STR_GETTER);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        StrAttrContainsKeywords predicate =
                new StrAttrContainsKeywords(new HashSet<>(keywords), Person.NAME_STR_GETTER);

        String expected = StrAttrContainsKeywords.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
