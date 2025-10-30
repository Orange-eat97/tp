package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClosestCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input argument and creates a new ClosestCommand object
 */
public class ClosestCommandParser implements Parser<ClosestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ClosestCommand and returns a ClosestCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClosestCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClosestCommand.MESSAGE_USAGE), pe);
        }

        return new ClosestCommand(index);
    }
}
