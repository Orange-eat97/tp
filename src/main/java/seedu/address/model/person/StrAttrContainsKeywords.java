package seedu.address.model.person;

import static seedu.address.logic.parser.ParserUtil.PREFIX_SYMBOL;
import static seedu.address.model.person.Person.ADDRESS_STR_GETTER;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.KeywordMatch;

/**
 * Tests that a {@code Person}'s specified attribute matches any of the keywords given.
 * Does prefix-matching, meaning to check if any words in the attribute has the keyword as a prefix
 */
public class StrAttrContainsKeywords implements Predicate<Person> {
    private final Set<KeywordMatch> keywordMatches;
    private final Function<Person, String> attributeGetter;

    /**
     * Creates a StrAttrContainsKeywords predicate.
     * @param keywordMatches Keywords to search for.
     * @param attributeGetter Function to get the attribute in String form to search from a Person.
     */
    public StrAttrContainsKeywords(Set<KeywordMatch> keywordMatches, Function<Person, String> attributeGetter) {
        this.keywordMatches = keywordMatches;
        this.attributeGetter = attributeGetter;
    }

    private boolean isMatch(KeywordMatch keywordMatch, Person person) {
        String keyword = keywordMatch.keyword();
        return keywordMatch.isPrefix()
                ? StringUtil.containsWordPrefixIgnoreCase(attributeGetter.apply(person), keyword)
                : StringUtil.containsWordIgnoreCase(attributeGetter.apply(person), keyword);
    }

    /**
     * Tests that a {@code Person}'s attribute matches any of the keywords given.
     * @param person the person to be tested
     * @return true if any of the keywords match the attribute, false otherwise
     */
    @Override
    public boolean test(Person person) {
        return keywordMatches.stream()
                .anyMatch(keywordMatch -> isMatch(keywordMatch, person));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StrAttrContainsKeywords)) {
            return false;
        }

        StrAttrContainsKeywords otherStrAttrContainsKeywords = (StrAttrContainsKeywords) other;
        return keywordMatches.equals(otherStrAttrContainsKeywords.keywordMatches)
                && attributeGetter.equals(otherStrAttrContainsKeywords.attributeGetter);
    }

    @Override
    public int hashCode() {
        return keywordMatches.hashCode() + attributeGetter.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywordMatches).toString();
    }
}
