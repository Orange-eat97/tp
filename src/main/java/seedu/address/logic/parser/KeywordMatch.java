package seedu.address.logic.parser;

import static seedu.address.logic.parser.ParserUtil.PREFIX_SYMBOL;

public record KeywordMatch(String keyword, boolean isPrefix) {
    @Override
    public String toString() {
        return isPrefix ? keyword + PREFIX_SYMBOL : keyword;
    }
}

