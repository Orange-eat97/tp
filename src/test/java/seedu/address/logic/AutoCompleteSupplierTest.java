package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class AutoCompleteSupplierTest {

    @Test
    public void findBestMatchValidPrefix() {
        assertEquals("add", AutoCompleteSupplier.findBestMatch("ad"));
    }

    @Test
    public void findBestMatchInvalidPrefix() {
        assertEquals("", AutoCompleteSupplier.findBestMatch("A"));
        assertEquals("", AutoCompleteSupplier.findBestMatch("aD"));
        assertEquals("", AutoCompleteSupplier.findBestMatch("Ad"));
        assertEquals("", AutoCompleteSupplier.findBestMatch("sr"));
    }

    @Test
    public void giveAddSortFindParamValidText() {
        assertEquals("n/", AutoCompleteSupplier.giveAddSortFindParams("sort p/"));
        assertEquals("p/", AutoCompleteSupplier.giveAddSortFindParams("add n/"));
        assertEquals("t/", AutoCompleteSupplier.giveAddSortFindParams("add n/ p/ e/ a/ r/ t/"));
    }

    @Test
    public void giveAddSortFindParamInvalidText() {
        assertNull(AutoCompleteSupplier.giveAddSortFindParams(""));
        assertNull(AutoCompleteSupplier.giveAddSortFindParams("Add"));
        assertNull(AutoCompleteSupplier.giveAddSortFindParams("edit"));
        assertNull(AutoCompleteSupplier.giveEditParams("find n/ p/ e/ a/ r/ t/"));
    }

    @Test
    public void giveEditParamsValidPrefix() {
        assertEquals("n/", AutoCompleteSupplier.giveEditParams(""));
        assertEquals("n/", AutoCompleteSupplier.giveEditParams("edit "));
        assertEquals("n/", AutoCompleteSupplier.giveEditParams("Edit"));
    }

    @Test
    public void giveEditParamsInvalidPrefix() {
        assertNull(AutoCompleteSupplier.giveEditParams("edit n/ p/ e/ a/ r/ t/"));
    }

    @Test
    public void makeTailWithCommandReturnSlash() {
        assertEquals("/", AutoCompleteSupplier.makeTail("add", "n/", "n"));
        assertEquals("n/", AutoCompleteSupplier.makeTail("add", "n/", null));
        assertEquals("n/", AutoCompleteSupplier.makeTail("add", "n/", ""));
        assertEquals("/", AutoCompleteSupplier.makeTail("add", "n/", "p"));
        assertEquals("", AutoCompleteSupplier.makeTail("add", "n/", "q"));
    }

    @Test
    public void makeTailWithCommandReturnFullPrefix() {
        assertEquals("n/", AutoCompleteSupplier.makeTail("add", "n/", null));
        assertEquals("n/", AutoCompleteSupplier.makeTail("add", "n/", ""));
    }

    @Test
    public void makeTailWithCommandInvalidAttributeLetter() {
        assertEquals("", AutoCompleteSupplier.makeTail("add", "n/", "q"));
    }



    @Test
    public void makeTailNoCommandReturnEmpty() {
        assertEquals("", AutoCompleteSupplier.makeTail("add", "add"));
        assertEquals("", AutoCompleteSupplier.makeTail("add", "q"));
        assertEquals("", AutoCompleteSupplier.makeTail(null, "q"));
        assertEquals("", AutoCompleteSupplier.makeTail("n/", "q"));
        assertEquals("", AutoCompleteSupplier.makeTail("add", "addition"));
    }

    @Test
    public void makeTailNoCommandReturnCommand() {
        assertEquals("dd", AutoCompleteSupplier.makeTail("add", "a"));
        assertEquals("add", AutoCompleteSupplier.makeTail("add", null));
    }

}
