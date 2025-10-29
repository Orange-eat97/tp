package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.NaturalOrderComparator.NATURAL_ORDER_COMPARATOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REGION, PREFIX_TAG);

        if (!checkPrefixesPresent(
                argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_REGION, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REGION, PREFIX_TAG);

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
                personComparator = Comparator.comparing(personPrefixValue, NATURAL_ORDER_COMPARATOR);

            } else {
                personComparator = personComparator.thenComparing(personPrefixValue, NATURAL_ORDER_COMPARATOR);
            }

        }

        String description = "\n• " + String.join("\n• ", labels);
        return new SortCommand(personComparator, description);

    }

    private Function<Person, String> getPersonPrefixValue(Prefix prefix) throws ParseException {
        switch (prefix.getPrefix()) {
        case "n/":
            return Person.NAME_STR_GETTER;
        case "p/":
            return Person.PHONE_STR_GETTER;
        case "e/":
            return Person.EMAIL_STR_GETTER;
        case "t/":
            return Person.ROLE_TAG_STR_GETTER;
        case "a/":
            return Person.ADDRESS_STR_GETTER;
        case "r/":
            return Person.REGION_STR_GETTER;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        }
    }

    private String getPrefixLabel(Prefix prefix) throws ParseException {
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
