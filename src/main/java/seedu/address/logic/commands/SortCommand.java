package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts people according to specified category
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted accordingly";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons according to your "
            + "specified category.\n"
            + "Parameters: "
            + PREFIX_NAME + " "
            + PREFIX_PHONE + " "
            + PREFIX_EMAIL + " "
            + PREFIX_ADDRESS + " "
            + PREFIX_TAG + " \n"
            + "Example: " + COMMAND_WORD + " n/";

    private final Comparator<Person> personComparator;

    /**
     * Creates a SortCommand to sort according to the category
     * @param comparator
     */
    public SortCommand(Comparator<Person> comparator) {
        this.personComparator = comparator;

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateSortedPersonList(personComparator);
        return new CommandResult(MESSAGE_SUCCESS);

    }
}
