package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Region;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    private static record ValidationRule(Predicate<String> validator, String messageConstraint){}
    private static final Map<Prefix, ValidationRule> VALIDATION_RULES = Map.of(
            PREFIX_NAME, new ValidationRule(Name::isValidName, Name.MESSAGE_CONSTRAINTS),
            PREFIX_ADDRESS, new ValidationRule(Address::isValidAddress, Address.MESSAGE_CONSTRAINTS),
            PREFIX_EMAIL, new ValidationRule(Email::isValidEmail, Email.MESSAGE_CONSTRAINTS),
            PREFIX_PHONE, new ValidationRule(Phone::isValidPhone, Phone.MESSAGE_CONSTRAINTS),
            PREFIX_REGION, new ValidationRule(Region::isValidRegion, Region.MESSAGE_CONSTRAINTS),
            PREFIX_TAG, new ValidationRule(Tag::isValidTagName, Tag.MESSAGE_CONSTRAINTS)
    );

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code oneBasedIndices} into a {@code List<Index>} and returns it.
     * @throws ParseException if any specified index is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseIndices(String oneBasedIndices) throws ParseException {
        String[] separatedIndices = StringUtil.getAllElements(oneBasedIndices.trim());
        List<Index> actualIndices = new ArrayList<>();
        for (String index : separatedIndices) {
            actualIndices.add(parseIndex(index));
        }
        return actualIndices;
    }

    /**
     * Parses {@code keywordStr} into a {@code Set<String>} (keywordStr) and returns it.
     */
    public static Set<String> parseKeywords(String keywordStr, Prefix prefix) throws ParseException {
        requireNonNull(keywordStr);
        String trimmedKeywords = keywordStr.trim();
        List<String> separatedKeywords = List.of(StringUtil.getAllElements(trimmedKeywords));

        ValidationRule rule = VALIDATION_RULES.get(prefix);
        if (rule == null) {
            throw new UnsupportedOperationException("Prefix not supported for attribute validation.");
        }
        if (!separatedKeywords.stream().allMatch(rule.validator)) {
            throw new ParseException(rule.messageConstraint);
        }

        return new HashSet<>(separatedKeywords);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String regionName} into a {@code Region}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code regionName} is invalid.
     */
    public static Region parseRegion(String regionName) throws ParseException {
        requireNonNull(regionName);
        String trimmedRegionName = regionName.trim();
        if (!Region.isValidRegion(trimmedRegionName)) {
            throw new ParseException(Region.MESSAGE_CONSTRAINTS);
        }
        return new Region(trimmedRegionName);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
