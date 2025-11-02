package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid names
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // invalid non-alphanumeric characters
        assertFalse(Name.isValidName("-'")); // only valid non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains invalid symbol
        assertFalse(Name.isValidName("12345")); // purely numeric
        assertFalse(Name.isValidName("12-34")); // still purely numeric with punctuation
        assertFalse(Name.isValidName("123 456 789")); // all digits with spaces
        assertFalse(Name.isValidName("A".repeat(51))); // exceeds 50 characters

        // valid names
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("Peter O'Brien")); // contains apostrophe
        assertTrue(Name.isValidName("Anna-Marie")); // contains hyphen
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("A1")); // contains both letter and digit
        assertTrue(Name.isValidName("A".repeat(50))); // exactly 50 characters
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
