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

import seedu.address.logic.parser.Prefix;
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
            + "specified attribute.\n"
            + "NOTE: Sort command prefixes should not have values.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "] "
            + "[" + PREFIX_PHONE + "] "
            + "[" + PREFIX_EMAIL + "] "
            + "[" + PREFIX_ADDRESS + "] "
            + "[" + PREFIX_REGION + "] "
            + "[" + PREFIX_TAG + "]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " " + PREFIX_PHONE;

    public static final List<String> PARAMS = List.of("n/", "p/", "e/", "a/", "r/", "t/");

    private final Comparator<Person> personComparator;
    private final String description;
    private final List<Prefix> prefixList;


    /**
     * Creates a SortCommand to sort according to the category
     *
     * @param comparator
     */
    public SortCommand(Comparator<Person> comparator, List<Prefix> prefixList) {
        this.personComparator = comparator;
        this.prefixList = prefixList;
        this.description = null;

    }

    /**
     * Creates a SortCommand specifically for closest command
     * @param comparator
     * @param description
     */
    public SortCommand(Comparator<Person> comparator, String description) {
        this.personComparator = comparator;
        this.prefixList = null;
        this.description = description;

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateDisplayList(personComparator);
        String prefixDescription;
        String sortStatusText;
        if (prefixList != null) {
            // normal sort command
            prefixDescription = createDescription(prefixList);
            sortStatusText = createSortStatusText(prefixList);

        } else {
            // closest command
            prefixDescription = "\n• " + description;
            sortStatusText = description;

        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, prefixDescription),
                false, false, sortStatusText, null);

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
        return prefixList.equals(prefixList);

    }

    private String createDescription(List<Prefix> prefixList) {
        StringBuilder description = new StringBuilder();
        for (Prefix prefix : prefixList) {
            description.append("• ").append(getPrefixLabel(prefix)).append("\n");

        }

        return "\n" + description.toString();
    }

    private String createSortStatusText(List<Prefix> prefixList) {
        StringBuilder sortStatusText = new StringBuilder();
        for (Prefix prefix : prefixList) {
            sortStatusText.append(getPrefixLabel(prefix)).append(", ");

        }

        sortStatusText.setLength(sortStatusText.length() - 2); // delete last ", "

        return sortStatusText.toString();
    }

    private String getPrefixLabel(Prefix prefix) {
        switch (prefix.getPrefix()) {
        case "n/":
            return "name";
        case "p/":
            return "phone number";
        case "e/":
            return "email";
        case "t/":
            return "volunteer/beneficiary tags";
        case "a/":
            return "address";
        case "r/":
            return "region";
        default:
            throw new UnsupportedOperationException("Prefix not supported for label.");

        }
    }

}
