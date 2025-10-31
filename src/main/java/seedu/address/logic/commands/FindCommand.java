package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.KeywordMatch;
import seedu.address.logic.parser.Prefix;
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
            "Listed %1$d persons matching the following attribute keywords\n";
    public static final List<String> PARAMS = List.of("n/", "p/", "e/", "a/", "r/", "t/");
    private static final Map<Prefix, String> PREFIX_LABELS = Map.of(
            PREFIX_NAME, "name",
            PREFIX_ADDRESS, "address",
            PREFIX_PHONE, "phone number",
            PREFIX_REGION, "region",
            PREFIX_EMAIL, "email",
            PREFIX_TAG, "tag"
    );
    private final Predicate<Person> predicate;
    private final String description;
    private final String statusText;


    /**
     * Creates FindCommand to filter persons based on predicates.
     * @param predicate a filter for persons
     * @param prefixMatches mapping of attribute {@code Prefix} to keywords, used for description and status
     */
    public FindCommand(Predicate<Person> predicate, Map<Prefix, Set<KeywordMatch>> prefixMatches) {
        this.predicate = predicate;
        this.description = createDescription(prefixMatches);
        this.statusText = createStatusText(prefixMatches);
    }

    private static String createDescription(Map<Prefix, Set<KeywordMatch>> prefixMatches) {
        if (prefixMatches.isEmpty()) {
            return "";
        }
        return "• " + prefixMatches.entrySet().stream()
                .map(
                        entry -> {
                            String keywords = getKeywords(entry.getValue());
                            return PREFIX_LABELS.get(entry.getKey()) + ": " + keywords;
                        })
                .collect(Collectors.joining("\n• "));
    }

    private String createStatusText(Map<Prefix, Set<KeywordMatch>> prefixMatches) {
        if (prefixMatches.isEmpty()) {
            return "";
        }
        return prefixMatches.entrySet().stream()
                .map(
                        entry -> {
                            String keywords = getKeywords(entry.getValue());
                            return entry.getKey().getPrefix() + keywords;
                        })
                .collect(Collectors.joining(" "));
    }

    private static String getKeywords(Set<KeywordMatch> keywordMatches) {
        return keywordMatches.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
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
