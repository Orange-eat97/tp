package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Parses input argument and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!checkPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        List<Prefix> prefixList = argMultimap.getAllPrefixes().stream()
                .filter(p -> !p.getPrefix().isEmpty())
                .collect(Collectors.toList());

        List<String> labels = new ArrayList<>();

        Comparator<Person> personComparator = null;
        for (int i = 0; i < prefixList.size(); i++) {
            Prefix prefix = prefixList.get(i);
            Function<Person, String> personPrefixValue = getPersonPrefixValue(prefix);
            labels.add(getPrefixLabel(prefix));
            if (i == 0) {
                personComparator = Comparator.comparing(personPrefixValue);

            } else {
                personComparator = personComparator.thenComparing(personPrefixValue);
            }

        }

        String description = String.join(", ", labels);
        System.out.println(description);
        return new SortCommand(personComparator, description);

    }

    private Function<Person, String> getPersonPrefixValue(Prefix prefix) throws ParseException {
        switch (prefix.getPrefix()) {
        case "n/":
            return person -> person.getName().fullName;
        case "p/":
            return person -> person.getPhone().value;
        case "e/":
            return person -> person.getEmail().value;
        case "t/":
            return person -> person.getTags().iterator().next().tagName;
        case "a/":
            return person -> person.getAddress().value;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        }
    }

    private String getPrefixLabel(Prefix prefix) throws ParseException {
        switch (prefix.getPrefix()) {
        case "n/":
            return "name";
        case "p/":
            return "phone";
        case "e/":
            return "email";
        case "t/":
            return "tag";
        case "a/":
            return "address";
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        }
    }


    /**
     * Returns true if any prefixes are present in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean checkPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
