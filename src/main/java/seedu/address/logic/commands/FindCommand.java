package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose specified fields contain "
            + "any of the specified case-insensitive keywords for each field, "
            + "matching either full words or optionally prefixes.\n"
            + "To match prefix, add % to the end of keyword. Example: Al% matches Alex Lee and Tan Aloysius. "
            + "NOTE: Prefix matching is NOT supported for address attribute. \n"
            + "Displays matching persons in a numbered list.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME_KEYWORDS] "
            + "[" + PREFIX_PHONE + "PHONE_KEYWORDS] "
            + "[" + PREFIX_EMAIL + "EMAIL_KEYWORDS] "
            + "[" + PREFIX_ADDRESS + "ADDRESS_KEYWORDS] "
            + "[" + PREFIX_REGION + "REGION_KEYWORDS] "
            + "[" + PREFIX_TAG + "TAG_KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Al% Bob Charlie" + " "
            + PREFIX_TAG + "benef%";
    public static final String FIND_SUCCESS_OVERVIEW =
            "Listed %1$d persons matching the following attribute keywords:\n";
    public static final List<String> PARAMS = List.of("n/", "p/", "e/", "a/", "r/", "t/");
    private final Predicate<Person> predicate;
    private final String description;
    private final String statusText;


    /**
     * Creates FindCommand to filter persons based on predicates.
     * @param predicate a filter for persons
     * @param description describes the predicate filters, part of the success message displayed
     * @param statusText brief overview of the filters
     */
    public FindCommand(Predicate<Person> predicate, String description, String statusText) {
        this.predicate = predicate;
        this.description = description;
        this.statusText = statusText;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateDisplayList(predicate);
        String overview = String.format(FIND_SUCCESS_OVERVIEW, model.getDisplayList().size());
        return new CommandResult(
                overview + description, false, false,
                null, statusText);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
