package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class SortCommandParser implements Parser<SortCommand> {


    public SortCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!checkPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        List<Prefix> prefixList = argMultimap.getAllPrefixes();
        Comparator<Person> personComparator = null;
        for (int i = 0; i < prefixList.size(); i++) {
            Prefix prefix = prefixList.get(i);
            if (i == 0) {
                personComparator = firstComparator(prefix);

            } else {
                nextComparator(prefix, personComparator);

            }

        }

        return new SortCommand(personComparator);

    }

    private Comparator<Person> firstComparator(Prefix prefix) throws ParseException {
        switch (prefix.getPrefix()) {
            case "n/":
                return Comparator.comparing(person -> person.getName().fullName);
            case "p/":
                return Comparator.comparing(person -> person.getPhone().value);
            case "e/":
                return Comparator.comparing(person -> person.getEmail().value);
            case "t/":
               return Comparator.comparing(person -> person.getTags().iterator().next().tagName);
            case "a/":
                return Comparator.comparing(person -> person.getName().fullName);
            default:
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        }
    }

    private Comparator<Person> nextComparator(Prefix prefix, Comparator<Person> comparator) throws ParseException {
        switch (prefix.getPrefix()) {
            case "n/":
                return comparator.thenComparing(person -> person.getName().fullName);
            case "p/":
                return comparator.thenComparing(person -> person.getPhone().value);
            case "e/":
                return comparator.thenComparing(person -> person.getEmail().value);
            case "t/":
                return comparator.thenComparing(person -> person.getTags().iterator().next().tagName);
            case "a/":
                return comparator.thenComparing(person -> person.getName().fullName);
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
