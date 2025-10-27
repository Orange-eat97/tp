package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.parseKeywords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ChainedPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.StrAttrContainsKeywords;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REGION, PREFIX_TAG);
        if (!atLeastOnePrefixPresent(
                argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REGION, PREFIX_TAG)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REGION, PREFIX_TAG);

        ArrayList<Predicate<Person>> predicates = new ArrayList<>();

        // build predicate for each attribute, will throw parseException if any invalid keywords are provided
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String nameKeywords = argMultimap.getValue(PREFIX_NAME).get();
            predicates.add(buildPredicate(
                    parseKeywords(nameKeywords, PREFIX_NAME), Person.NAME_STR_GETTER));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String phoneKeywords = argMultimap.getValue(PREFIX_PHONE).get();
            predicates.add(buildPredicate(
                    parseKeywords(phoneKeywords, PREFIX_PHONE), Person.PHONE_STR_GETTER));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String emailKeywords = argMultimap.getValue(PREFIX_EMAIL).get();
            predicates.add(buildPredicate(
                    parseKeywords(emailKeywords, PREFIX_EMAIL), Person.EMAIL_STR_GETTER));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String addressKeywords = argMultimap.getValue(PREFIX_ADDRESS).get();
            predicates.add(buildPredicate(
                    parseKeywords(addressKeywords, PREFIX_ADDRESS), Person.ADDRESS_STR_GETTER));
        }
        if (argMultimap.getValue(PREFIX_REGION).isPresent()) {
            String regionKeywords = argMultimap.getValue(PREFIX_REGION).get();
            predicates.add(buildPredicate(
                    parseKeywords(regionKeywords, PREFIX_REGION), Person.REGION_STR_GETTER));
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String tagKeywords = argMultimap.getValue(PREFIX_TAG).get();
            predicates.add(buildPredicate(
                    parseKeywords(tagKeywords, PREFIX_TAG), Person.TAG_STR_GETTER));
        }

        if (predicates.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new ChainedPredicate(predicates));
    }

    /**
     * Builds predicate based on a string of keywords and a Person attribute getter
     * @param keywords containing keywords to search for
     * @return predicate that evaluates to true if at least one of the keywords is found in the attribute
     */
    private static StrAttrContainsKeywords buildPredicate(
            Set<String> keywords, Function<Person, String> attributeGetter) {
        return new StrAttrContainsKeywords(keywords, attributeGetter);
    }
    private static boolean atLeastOnePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Arrays.stream(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
