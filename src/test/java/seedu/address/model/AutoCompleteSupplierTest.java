package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.ui.AutoCompleteSupplier;

public class AutoCompleteSupplierTest {

    private AutoCompleteSupplier autoCompleteSupplier = new AutoCompleteSupplier();

    @Test
    public void testAutoCompleteSupplier() {
        assertEquals(autoCompleteSupplier.findBestMatch("a"), AddCommand.COMMAND_WORD);
        assertEquals(autoCompleteSupplier.findBestMatch("li"), ListCommand.COMMAND_WORD);
    }
}
