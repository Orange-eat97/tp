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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ChainedPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.StrAttrContainsKeywords;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Map<Prefix, Function<Person, String>> ATTRIBUTE_GETTERS = Map.of(
            PREFIX_NAME, Person.NAME_STR_GETTER,
            PREFIX_ADDRESS, Person.ADDRESS_STR_GETTER,
            PREFIX_PHONE, Person.PHONE_STR_GETTER,
            PREFIX_REGION, Person.REGION_STR_GETTER,
            PREFIX_EMAIL, Person.EMAIL_STR_GETTER,
            PREFIX_TAG, Person.TAG_STR_GETTER
    );

    private static final Map<Prefix, String> PREFIX_LABELS = Map.of(
            PREFIX_NAME, "name",
            PREFIX_ADDRESS, "address",
            PREFIX_PHONE, "phone number",
            PREFIX_REGION, "region",
            PREFIX_EMAIL, "email",
            PREFIX_TAG, "tag"
    );

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        Prefix[] allPrefixes = new Prefix[] {
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REGION, PREFIX_TAG
        };
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, allPrefixes);
        if (!atLeastOnePrefixPresent(argMultimap, allPrefixes)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(allPrefixes);

        ArrayList<Predicate<Person>> predicates = new ArrayList<>();
        Map<Prefix, Set<KeywordMatch>> prefixMatches = new HashMap<>();

        // build predicate for each attribute, will throw parseException if any invalid keywords are provided
        for (Prefix prefix : allPrefixes) {
            if (argMultimap.getValue(prefix).isPresent()) {
                String keywords = argMultimap.getValue(prefix).get();
                Function<Person, String> getter = ATTRIBUTE_GETTERS.get(prefix);
                Set<KeywordMatch> keywordMatches = parseKeywords(keywords, prefix);
                prefixMatches.put(prefix, keywordMatches);
                predicates.add(buildPredicate(keywordMatches, getter));
            }
        }

        if (predicates.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(
                new ChainedPredicate(predicates),
                createDescription(prefixMatches), createStatusText(prefixMatches));
    }

    private static String createDescription(Map<Prefix, Set<KeywordMatch>> prefixMatches) {
        return prefixMatches.entrySet().stream()
            .map(
                entry -> {
                    String keywords = getKeywords(entry.getValue());
                    return PREFIX_LABELS.get(entry.getKey()) + ": " + keywords;
                })
            .collect(Collectors.joining("\n"));
    }

    private static String createStatusText(Map<Prefix, Set<KeywordMatch>> prefixMatches) {
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

    /**
     * Builds predicate based on a string of keywords and a Person attribute getter
     * @param keywords containing keywords to search for
     * @return predicate that evaluates to true if at least one of the keywords is found in the attribute
     */
    private static StrAttrContainsKeywords buildPredicate(
            Set<KeywordMatch> keywords, Function<Person, String> attributeGetter) {
        return new StrAttrContainsKeywords(keywords, attributeGetter);
    }
    private static boolean atLeastOnePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Arrays.stream(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
