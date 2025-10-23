package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's geographical region in the address book. Guarantees:
 * immutable; is valid as declared in {@link #isValidRegion(String)}
 */
public class Region {

    public static final String MESSAGE_CONSTRAINTS = """
            Region can take any valid region name as a value, and it should not be blank.
            Valid regions are: %s.""".formatted(String.join(", ", ValidRegion.getAllRegionNames()));

    public final ValidRegion value;

    /**
     * Constructs a {@code Region}.
     *
     * @param regionName A valid region's name.
     */
    public Region(String regionName) {
        requireNonNull(regionName);
        checkArgument(isValidRegion(regionName), MESSAGE_CONSTRAINTS);
        this.value = ValidRegion.fromName(regionName);
    }

    /**
     * Returns true if a given string is a valid region name.
     */
    public static boolean isValidRegion(String test) {
        return ValidRegion.isValidRegion(test);
    }

    /**
     * @return distance from this region to the other region
     */
    public int getDistanceFrom(Region other) {
        return ValidRegion.getDistance(other.value, this.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Region)) {
            return false;
        }

        Region otherAddress = (Region) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
