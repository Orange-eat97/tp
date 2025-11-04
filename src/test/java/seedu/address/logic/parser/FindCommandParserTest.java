package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REGION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.ChainedPredicate;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Region;
import seedu.address.model.person.StrAttrContainsKeywords;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_noFieldsSpecified_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preamblePresent_throwsParseException() {
        // any non-empty preamble should cause invalid command format
        String inputWithPreamble = "unexpectedPreamble " + PHONE_DESC_AMY;
        assertParseFailure(parser, inputWithPreamble,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // single invalid attribute keyword
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_REGION_DESC, Region.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid attribute keywords
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_ADDRESS_DESC, Name.MESSAGE_CONSTRAINTS);

        // One invalid and one valid keyword for one attribute
        assertParseFailure(parser, INVALID_NAME_DESC + VALID_NAME_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        String input = PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertParseFailure(parser, input,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }

    @Test
    public void parse_validArgsWithSpaces_returnsFindCommand() {
        Set<KeywordMatch> keywordMatches = List.of("Alice", "Bob").stream()
                .map(keyword -> new KeywordMatch(keyword, false))
                .collect(Collectors.toSet());
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(
                new ChainedPredicate(
                    List.of(new StrAttrContainsKeywords(keywordMatches, Person.NAME_STR_GETTER))),
                new HashMap<>()
        );
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_NAME + " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validPrefixes_returnsFindCommand() {
        Set<KeywordMatch> nameKeywordMatches = List.of("Ali", "Bo").stream()
                .map(keyword -> new KeywordMatch(keyword, true))
                .collect(Collectors.toSet());
        Set<KeywordMatch> phoneKeywordMatches = List.of("123", "678").stream()
                .map(keyword -> new KeywordMatch(keyword, true))
                .collect(Collectors.toSet());

        HashMap<Prefix, Set<KeywordMatch>> prefixMatches = new HashMap<>();
        prefixMatches.put(PREFIX_NAME, nameKeywordMatches);

        // only name prefixes provided
        FindCommand expectedFindCommand = new FindCommand(
                new ChainedPredicate(List.of(
                        new StrAttrContainsKeywords(nameKeywordMatches, Person.NAME_STR_GETTER)
                    )),
                prefixMatches);
        String userInput = " " + PREFIX_NAME + "Ali% Bo%";
        assertParseSuccess(parser, userInput, expectedFindCommand);

        // name and phone prefixes provided
        prefixMatches.put(PREFIX_PHONE, phoneKeywordMatches);
        expectedFindCommand = new FindCommand(
                new ChainedPredicate(List.of(
                        new StrAttrContainsKeywords(nameKeywordMatches, Person.NAME_STR_GETTER),
                        new StrAttrContainsKeywords(phoneKeywordMatches, Person.PHONE_UNSPACED_STR_GETTER)
                    )),
                prefixMatches);

        userInput += " " + PREFIX_PHONE + "123% 678%";

        assertParseSuccess(parser, userInput, expectedFindCommand);
    }
}
