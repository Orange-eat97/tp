package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.StringUtil.normalizeInnerSpaces;

/**
 * Represents a Person's name in the address book. Guarantees: immutable; is
 * valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names and prefixes should only contain alphanumeric characters, hyphens, and apostrophes, "
            + "must not be blank, must not exceed 50 characters, and names cannot be purely numeric.";

    /*
    * Names:
    * - Start with an alphanumeric character
    * - May include spaces, apostrophes, or hyphens
    * - Must contain at least one letter (not purely digits)
    * - Length 1–50 characters
    */
    public static final String VALIDATION_REGEX = "^(?=.*[A-Za-z])[\\p{Alnum}][\\p{Alnum}'\\s-]{0,49}$";

    /*
    * Prefixes:
    * - Start with alphanumeric
    * - Can include apostrophes or hyphens
    * - No spaces
    * - Length 1–50 characters
    */
    public static final String PREFIX_VALIDATION_REGEX = "^[\\p{Alnum}][\\p{Alnum}'-]{0,49}$";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = normalizeInnerSpaces(name);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return normalizeInnerSpaces(test).matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid name prefix.
     */
    public static boolean isValidNamePrefix(String test) {
        return test.matches(PREFIX_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
