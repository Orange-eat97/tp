package seedu.address.model.person;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class StrAttrContainsKeywords implements Predicate<Person> {
    private final Set<String> keywords;
    private final Function<Person, String> attributeGetter;

    public StrAttrContainsKeywords(List<String> keywords, Function<Person, String> attributeGetter) {
        this.keywords = new HashSet<>(keywords);
        this.attributeGetter = attributeGetter;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(attributeGetter.apply(person), keyword));
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
        return keywords.equals(otherStrAttrContainsKeywords.keywords)
                && attributeGetter.equals(otherStrAttrContainsKeywords.attributeGetter);
    }

    @Override
    public int hashCode() {
        return keywords.hashCode() + attributeGetter.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
