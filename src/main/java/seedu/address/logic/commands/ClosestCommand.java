package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Region;
import seedu.address.model.person.StrAttrContainsKeywords;

/**
 * Sorts people according to how close their region is to the region of the
 * identified person.
 */
public class ClosestCommand extends Command {

    public static final String COMMAND_WORD = "closest";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all persons according to distance to region of the person identified"
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) " + "Example: " + COMMAND_WORD + " 1 ";

    private final Index index;

    /**
     * Creates a Closest command that sorts people according to the closeness of
     * their region to the region of the identified person
     *
     * @param index of the person in the filtered person list to edit
     */
    public ClosestCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getDisplayList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToSortBy = lastShownList.get(index.getZeroBased());

        Comparator<Person> personComparator = createClosestComparator(personToSortBy);

        model.updateDisplayList(new StrAttrContainsKeywords(
            Set.of("volunteer"),
            Person.TAG_STR_GETTER));

        return new SortCommand(personComparator,
                "closest volunteer to %s".formatted(personToSortBy.getRegion().value.getDisplayName())).execute(model);
    }

    /**
     * Creates a Person comparator that sorts people according to how close their region is to the region of the
     * given person.
     *
     * @param personToCompareTo is the person whose region is used to sort the closeness of each person's region to
     * @return personComparator
     */
    public static Comparator<Person> createClosestComparator(Person personToCompareTo) {
        Region regionToCompareTo = personToCompareTo.getRegion();

        return (Person o1, Person o2) -> {
            if (o1.equals(personToCompareTo)) {
                return -1;
            }
            if (o2.equals(personToCompareTo)) {
                return 1;
            }

            Region r1 = o1.getRegion();
            Region r2 = o2.getRegion();

            int distDiff = r1.getDistanceFrom(regionToCompareTo) - r2.getDistanceFrom(regionToCompareTo);

            int nameDiff = r1.value.getDisplayName().compareTo(r2.value.getDisplayName());

            return distDiff == 0 ? nameDiff : distDiff;
        };
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClosestCommand)) {
            return false;
        }

        ClosestCommand otherClosestCommand = (ClosestCommand) other;
        return index.equals(otherClosestCommand.index);

    }
}
