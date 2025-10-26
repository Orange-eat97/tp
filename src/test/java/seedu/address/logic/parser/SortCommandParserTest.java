package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.NaturalOrderComparator.NATURAL_ORDER_COMPARATOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.Person;



public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_validPrefixArgs_returnsSortCommand() {
        Comparator<Person> comparator = Comparator.comparing(Person.NAME_STR_GETTER, NATURAL_ORDER_COMPARATOR);
        SortCommand expectedSortCommand = new SortCommand(comparator, "name");
        assertParseSuccess(parser, " " + PREFIX_NAME, expectedSortCommand);

    }

    @Test void parse_multipleValidPrefixArgs_returnsSortCommand() {
        Comparator<Person> comparator = Comparator.comparing(Person.NAME_STR_GETTER, NATURAL_ORDER_COMPARATOR);
        comparator = comparator.thenComparing(Person.ADDRESS_STR_GETTER, NATURAL_ORDER_COMPARATOR);
        comparator = comparator.thenComparing(Person.EMAIL_STR_GETTER, NATURAL_ORDER_COMPARATOR);

        SortCommand expectedSortCommand = new SortCommand(comparator, "name, address, email");
        assertParseSuccess(parser, " " + PREFIX_NAME + ", "
                + PREFIX_ADDRESS + ", " + PREFIX_EMAIL, expectedSortCommand);

    }
}
