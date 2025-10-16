package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RegionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Region(null));
    }

    @Test
    public void constructor_invalidRegion_throwsIllegalArgumentException() {
        String invalidRegion = "";
        assertThrows(IllegalArgumentException.class, () -> new Region(invalidRegion));
        assertThrows(IllegalArgumentException.class, () -> new Region("UNKNOWN_REGION"));
    }

    @Test
    public void isValidRegion() {
        // invalid regions
        assertFalse(Region.isValidRegion(""));
        assertFalse(Region.isValidRegion("UNKNOWN_REGION"));

        // valid regions (examples from your ValidRegion enum)
        assertTrue(Region.isValidRegion("WOODLANDS"));
        assertTrue(Region.isValidRegion("YISHUN"));
        assertTrue(Region.isValidRegion("TAMPINES"));
    }

    @Test
    public void equals() {
        Region woodlands = new Region("WOODLANDS");
        Region yishun = new Region("YISHUN");

        // same values -> returns true
        assertTrue(woodlands.equals(new Region("WOODLANDS")));

        // same object -> returns true
        assertTrue(woodlands.equals(woodlands));

        // different types -> returns false
        assertFalse(woodlands.equals("WOODLANDS"));

        // different values -> returns false
        assertFalse(woodlands.equals(yishun));
    }
}
