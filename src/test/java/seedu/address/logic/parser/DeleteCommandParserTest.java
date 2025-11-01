package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDICES_THREE_PERSONS;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;


/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_singleValidIndex_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(List.of(INDEX_FIRST_PERSON)));
    }

    @Test
    public void parse_singleInvalidIndex_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleValidIndexes_returnsDeleteCommand() {
        assertParseSuccess(parser, "3 2 1", new DeleteCommand(INDICES_THREE_PERSONS));
    }

    @Test
    public void parse_multipleValidIndexesWithExtraSpaces_returnsDeleteCommand() {
        assertParseSuccess(parser, " 3  2    1 ", new DeleteCommand(INDICES_THREE_PERSONS));
    }

    @Test
    public void parse_multipleValidAndInvalidIndexes_throwsParseException() {
        assertParseFailure(parser, "1 2 a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
