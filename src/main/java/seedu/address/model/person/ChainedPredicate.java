package seedu.address.model.person;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person} matches all the given predicates.
 * Mainly a wrapper to ensure proper predicate equality checking.
 */
public class ChainedPredicate implements Predicate<Person> {
    private Set<Predicate<Person>> predicates;

    public ChainedPredicate(List<Predicate<Person>> predicates) {
        this.predicates = new HashSet<>(predicates);
    }

    @Override
    public boolean test(Person person) {
        return predicates.stream().allMatch(predicate -> predicate.test(person));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ChainedPredicate)) {
            return false;
        }
        ChainedPredicate otherChainedPredicate = (ChainedPredicate) other;
        return predicates.equals(otherChainedPredicate.predicates);
    }

}
