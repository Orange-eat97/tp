package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClosestCommand;

/**
 * Contains unit tests for ClosestCommandParser.
 */
public class ClosestCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClosestCommand.MESSAGE_USAGE);

    private ClosestCommandParser parser = new ClosestCommandParser();

    @Test
    public void parse_invalidIndex_failure() {
        // empty input
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "   ", MESSAGE_INVALID_FORMAT);

        // non-numeric index
        assertParseFailure(parser, "abc", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // negative index
        assertParseFailure(parser, "-3", MESSAGE_INVALID_FORMAT);

        // extra arguments that corrupt parsing
        assertParseFailure(parser, "1 something", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 i/incorrect", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validIndex_success() {
        // first index
        assertParseSuccess(parser, "1", new ClosestCommand(INDEX_FIRST_PERSON));

        // other valid index
        assertParseSuccess(parser, "2", new ClosestCommand(INDEX_SECOND_PERSON));
    }
}
