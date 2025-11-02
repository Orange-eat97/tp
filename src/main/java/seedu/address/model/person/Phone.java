package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.StringUtil.normalizeInnerSpaces;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
        "Phone numbers should contain only digits and spaces, "
        + "must start and end with digits, have at least 3 digits, and must not exceed 50 characters.";

    /*
    * Allows digits and single or multiple inner spaces.
    * No leading/trailing spaces.
    * Total length between 3 and 50 characters.
    */
    public static final String VALIDATION_REGEX = "^[0-9](?:[0-9 ]{1,48}[0-9])?$";

    /*
    * Prefix version: allows only digits, no spaces.
    * Between 1 and 50 digits.
    */
    public static final String PREFIX_VALIDATION_REGEX = "^\\d{1,50}$";

    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = normalizeInnerSpaces(phone);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return normalizeInnerSpaces(test).matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if given string is a valid phone prefix
     */
    public static boolean isValidPhonePrefix(String test) {
        return test.matches(PREFIX_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
