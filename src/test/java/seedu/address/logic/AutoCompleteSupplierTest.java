package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class AutoCompleteSupplierTest {

    @Test
    public void findBestMatch_validPrefix_returnCommand() {
        assertEquals("add", AutoCompleteSupplier.findBestMatch("ad"));
    }

    @Test
    public void findBestMatch_invalidPrefix_returnEmpty() {
        assertEquals("", AutoCompleteSupplier.findBestMatch("A"));
        assertEquals("", AutoCompleteSupplier.findBestMatch("aD"));
        assertEquals("", AutoCompleteSupplier.findBestMatch("Ad"));
        assertEquals("", AutoCompleteSupplier.findBestMatch("sr"));
    }

    @Test
    public void giveAddSortFindParam_validText_returnParamSig() {
        assertEquals("n/", AutoCompleteSupplier.giveAddSortFindParams("sort p/"));
        assertEquals("p/", AutoCompleteSupplier.giveAddSortFindParams("add n/"));
        assertEquals("t/", AutoCompleteSupplier.giveAddSortFindParams("add n/ p/ e/ a/ r/ t/"));
    }

    @Test
    public void giveAddSortFindParam_invalidText_returnNull() {
        assertNull(AutoCompleteSupplier.giveAddSortFindParams(""));
        assertNull(AutoCompleteSupplier.giveAddSortFindParams("Add"));
        assertNull(AutoCompleteSupplier.giveAddSortFindParams("edit"));
        assertNull(AutoCompleteSupplier.giveAddSortFindParams("find n/ p/ e/ a/ r/ t/"));
    }

    @Test
    public void giveEditParams_validPrefix_returnParamSig() {
        assertEquals("n/", AutoCompleteSupplier.giveEditParams(""));
        assertEquals("n/", AutoCompleteSupplier.giveEditParams("edit "));
        assertEquals("n/", AutoCompleteSupplier.giveEditParams("Edit"));
    }

    @Test
    public void giveEditParams_invalidPrefix_returnNull() {
        assertNull(AutoCompleteSupplier.giveEditParams("edit n/ p/ e/ a/ r/ t/"));
    }

    @Test
    public void makeTailWithCommand_Param_returnSlash() {
        assertEquals("/", AutoCompleteSupplier.makeTail("add", "n/", "n"));
        assertEquals("/", AutoCompleteSupplier.makeTail("add", "n/", "p"));
    }

    @Test
    public void makeTailWithCommand_EmptyText_returnParamSlash() {
        assertEquals("n/", AutoCompleteSupplier.makeTail("add", "n/", null));
        assertEquals("n/", AutoCompleteSupplier.makeTail("add", "n/", ""));
    }

    @Test
    public void makeTailWithCommand_InvalidLetter_returnEmpty() {
        assertEquals("", AutoCompleteSupplier.makeTail("add", "n/", "q"));
    }

    @Test
    public void makeTailNoCommand_prefixCompleted_returnEmpty() {
        assertEquals("", AutoCompleteSupplier.makeTail("add", "add"));
    }

    @Test
    public void makeTailNoCommand_suggestionPrefixMismatch_returnEmpty() {
        assertEquals("", AutoCompleteSupplier.makeTail("add", "q"));
        assertEquals("", AutoCompleteSupplier.makeTail("n/", "q"));
    }

    @Test
    public void makeTailNoCommand_noSuggestion_returnEmpty() {
        assertEquals("", AutoCompleteSupplier.makeTail(null, "q"));
    }

    @Test
    public void makeTailNoCommand_prefiexLongerThanSuggestion_returnEmpty() {
        assertEquals("", AutoCompleteSupplier.makeTail("add", "addition"));
    }

    @Test
    public void makeTailNoCommand_prefixIsNullOrValidPrefix_returnCommand() {
        assertEquals("dd", AutoCompleteSupplier.makeTail("add", "a"));
        assertEquals("add", AutoCompleteSupplier.makeTail("add", null));
    }

}
