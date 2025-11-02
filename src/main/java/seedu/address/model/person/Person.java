package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.StringUtil.removeAllWhitespace;
import static seedu.address.commons.util.StringUtil.standardiseName;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    public static final Function<Person, String> NAME_STR_GETTER = person -> person.getName().fullName;
    public static final Function<Person, String> PHONE_UNSPACED_STR_GETTER =
            person -> removeAllWhitespace(person.getPhone().value);
    public static final Function<Person, String> EMAIL_STR_GETTER = person -> person.getEmail().value;
    public static final Function<Person, String> ADDRESS_STR_GETTER = person -> person.getAddress().value;
    public static final Function<Person, String> TAG_STR_GETTER =
            person -> person.getTags().stream().map(t -> t.tagName).collect(Collectors.joining(" "));
    public static final Function<Person, String> ROLE_TAG_STR_GETTER =
            person -> TAG_STR_GETTER.apply(person).contains("beneficiary") ? "beneficiary" : "volunteer";

    public static final Function<Person, String> REGION_STR_GETTER =
            person -> person.getRegion().value.getDisplayName();
    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Region region;
    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Region region, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, region, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.region = region;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Region getRegion() {
        return region;
    }
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name (ignoring special characters) and phone number (ignoring spaces).
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && removeAllWhitespace(otherPerson.getPhone().value).equals(removeAllWhitespace(getPhone().value))
                && standardiseName(otherPerson.getName().fullName).equals(standardiseName(getName().fullName));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && region.equals(otherPerson.region)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, region, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("region", region)
                .add("tags", tags)
                .toString();
    }

}
