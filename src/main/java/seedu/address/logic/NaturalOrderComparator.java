package seedu.address.logic;

import java.util.Comparator;

/**
 * Comparator that compares strings in a natural order instead of lexicographical order.
 * Natural order for alphanumeric strings is a human-friendly sorting method
 * that treats numbers as whole values instead of individual digits.
 * For example, "4th" comes before "10th".
 */
public class NaturalOrderComparator implements Comparator<String> {

    public static final Comparator<String> NATURAL_ORDER_COMPARATOR = new NaturalOrderComparator();

    /**
     * Private constructor so there is only one comparator and cannot be modified
     */
    private NaturalOrderComparator() {}

    @Override
    public int compare(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return 0;

        }

        if (s1 == null) {
            return -1;

        }

        if (s2 == null) {
            return 1;

        }

        int i1 = 0;
        int i2 = 0;
        int len1 = s1.length();
        int len2 = s2.length();

        while (i1 < len1 && i2 < len2) {
            char c1 = s1.charAt(i1);
            char c2 = s2.charAt(i2);

            //check both characters are starting sequence of number
            if (Character.isDigit(c1) && Character.isDigit(c2)) {
                int start1 = i1;
                int start2 = i2;
                while (i1 < len1 && Character.isDigit(s1.charAt(i1))) {
                    i1++;

                }

                while (i2 < len2 && Character.isDigit(s2.charAt(i2))) {
                    i2++;

                }

                int num1 = Integer.parseInt(s1.substring(start1, i1));
                int num2 = Integer.parseInt(s2.substring(start2, i2));

                // compare numbers if not equal
                if (num1 != num2) {
                    return Integer.compare(num1, num2);

                }

            } else {
                // Compare the characters normally
                int cmp = Character.compare(Character.toLowerCase(c1),
                        Character.toLowerCase(c2));

                if (cmp != 0) {
                    return cmp;

                }

                i1++;
                i2++;

            }
        }

        return Integer.compare(len1, len2);

    }
}
