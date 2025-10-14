package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.StrAttrContainsKeywords;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Predicate<Person> PREDICATE_FALSE = person -> false;
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        if (!atLeastOnePrefixPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty() ) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        // build predicate for each attribute
        Predicate<Person> namePredicate = argMultimap.getValue(PREFIX_NAME)
                .map( keywordStr -> buildPredicate(keywordStr, Person.NAME_STR_GETTER))
                .orElse(PREDICATE_FALSE);
        Predicate<Person> phonePredicate = argMultimap.getValue(PREFIX_PHONE)
                .map( keywordStr -> buildPredicate(keywordStr, Person.PHONE_STR_GETTER))
                .orElse(PREDICATE_FALSE);
        Predicate<Person> emailPredicate = argMultimap.getValue(PREFIX_EMAIL)
                .map( keywordStr -> buildPredicate(keywordStr, Person.EMAIL_STR_GETTER))
                .orElse(PREDICATE_FALSE);
        Predicate<Person> addressPredicate = argMultimap.getValue(PREFIX_ADDRESS)
                .map( keywordStr -> buildPredicate(keywordStr, Person.ADDRESS_STR_GETTER))
                .orElse(PREDICATE_FALSE);

        // TODO: use a different predicate for tags since it's a set of Tag objects
        Predicate<Person> tagPredicate = argMultimap.getValue(PREFIX_TAG)
                .map( keywordStr -> buildPredicate(keywordStr, person -> person.getTags().toString()))
                .orElse(PREDICATE_FALSE);

        if (namePredicate == PREDICATE_FALSE && phonePredicate == PREDICATE_FALSE
                && emailPredicate == PREDICATE_FALSE && addressPredicate == PREDICATE_FALSE
                && tagPredicate == PREDICATE_FALSE) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(namePredicate.or(phonePredicate)
                .or(emailPredicate)
                .or(addressPredicate)
                .or(tagPredicate));
    }

    /**
     * Builds predicate based on a string of keywords and a Person attribute getter
     * @param keywordString containing keywords to search for
     * @return predicate that evaluates to true if at least one of the keywords is found in the attribute
     */
    private static Predicate<Person> buildPredicate(String keywordString, Function<Person, String> attributeGetter) {
        String[] nameKeywords = keywordString.trim().split("\\s+");
        return new StrAttrContainsKeywords(Arrays.asList(nameKeywords), attributeGetter);
    }
    private static boolean atLeastOnePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Arrays.stream(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}