package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.KeywordMatch;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Region;
import seedu.address.model.person.StrAttrContainsKeywords;
import seedu.address.model.tag.Tag;

/**
 * Sorts people according to how close their region is to the region of the
 * identified person.
 */
public class ClosestCommand extends Command {

    public static final String COMMAND_WORD = "closest";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all persons according to distance to region of the person identified. "
            + "Displays only volunteers if selected beneficiary and only beneficiaries if selected volunteer. "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) " + "Example: " + COMMAND_WORD + " 1 ";

    public static final Predicate<Person> PREDICATE_SHOW_ALL_VOLUNTEERS = new StrAttrContainsKeywords(
            Set.of(new KeywordMatch("volunteer", false)), Person.TAG_STR_GETTER);

    public static final Predicate<Person> PREDICATE_SHOW_ALL_BENEFICIARY = new StrAttrContainsKeywords(
            Set.of(new KeywordMatch("beneficiary", false)), Person.TAG_STR_GETTER);

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

        boolean isPersonVolunteer = personToSortBy.getTags().contains(new Tag("volunteer"));
        String filteredByString = isPersonVolunteer ? "beneficiary" : "volunteer";

        CommandResult resultFind = new FindCommand(
            isPersonVolunteer ? PREDICATE_SHOW_ALL_BENEFICIARY : PREDICATE_SHOW_ALL_VOLUNTEERS,
            Map.of(PREFIX_TAG, Set.of(new KeywordMatch(filteredByString, false)))).execute(model);

        String regionName = personToSortBy.getRegion().value.getDisplayName();
        CommandResult resultSort = new SortCommand(personComparator, "closest volunteer to %s".formatted(regionName))
                .execute(model);

        return new CommandResult("Sorted and filtered to find closest %s to %s".formatted(filteredByString, regionName),
                false, false, resultSort.getSortStatusText(), resultFind.getFindStatusText());
    }

    /**
     * Creates a Person comparator that sorts people according to how close
     * their region is to the region of the given person.
     *
     * @param personToCompareTo is the person whose region is used to sort the
     *     closeness of each person's region to
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
