package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts people according to its categories.
 * Groups tags according to volunteer and beneficiary.
 *
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted according to %s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons according to your "
            + "specified category.\n"
            + "Parameters: "
            + PREFIX_NAME + " "
            + PREFIX_PHONE + " "
            + PREFIX_EMAIL + " "
            + PREFIX_ADDRESS + " "
            + PREFIX_REGION + " "
            + PREFIX_TAG + " \n"
            + "Example: " + COMMAND_WORD + " n/"
            + "[" + PREFIX_NAME + "NAME_KEYWORDS] "
            + "[" + PREFIX_PHONE + "PHONE_KEYWORDS] "
            + "[" + PREFIX_EMAIL + "EMAIL_KEYWORDS] "
            + "[" + PREFIX_ADDRESS + "ADDRESS_KEYWORDS] "
            + "[" + PREFIX_REGION + "REGION_KEYWORDS] "
            + "[" + PREFIX_TAG + "TAG_KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " " + PREFIX_PHONE;

    public static final List<String> PARAMS = List.of("n/", "p/", "e/", "a/", "r/", "t/");

    private final Comparator<Person> personComparator;
    private final String description;


    /**
     * Creates a SortCommand to sort according to the category
     * @param comparator
     */
    public SortCommand(Comparator<Person> comparator, String description) {
        this.personComparator = comparator;
        this.description = description;

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateDisplayList(personComparator);
        return new CommandResult(String.format(MESSAGE_SUCCESS, description));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return description.equals(description);

    }
}
