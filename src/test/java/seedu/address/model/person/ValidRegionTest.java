package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ValidRegionTest {
    @Test
    public void regionProperties_areValid() {
        List<String> validMainRegions = List.of("North", "North-East", "East", "Central", "West");
        for (ValidRegion region : ValidRegion.values()) {
            assertNotNull(region.getDisplayName(), "Display name should not be null");
            assertFalse(region.getDisplayName().isEmpty(), "Display name should not be empty");
            assertNotNull(region.getMainRegion(), "Main region should not be null");
            assertFalse(region.getMainRegion().isEmpty(), "Main region should not be empty");
            assertTrue(validMainRegions.contains(region.getMainRegion()),
                    region + " has an invalid main region: " + region.getMainRegion());
        }
    }

    @Test
    public void fromName_validNames_returnsCorrectEnum() {
        assertEquals(ValidRegion.WOODLANDS, ValidRegion.fromName("woodlands"));
        assertEquals(ValidRegion.YISHUN, ValidRegion.fromName("yishun"));
    }

    @Test
    public void fromName_invalidNames_returnsNull() {
        assertNull(ValidRegion.fromName("notarealregion"));
        assertNull(ValidRegion.fromName(""));
        assertNull(ValidRegion.fromName(null));
    }

    @Test
    public void isValidRegion_validNames_returnsTrue() {
        assertTrue(ValidRegion.isValidRegion("woodlands"));
        assertTrue(ValidRegion.isValidRegion("yishun"));
    }

    @Test
    public void isValidRegion_invalidNames_returnsFalse() {
        assertFalse(ValidRegion.isValidRegion("notarealregion"));
        assertFalse(ValidRegion.isValidRegion(""));
        assertFalse(ValidRegion.isValidRegion(null));
    }

    @Test
    public void adjacency_isSymmetric() {
        for (ValidRegion region : ValidRegion.values()) {
            for (ValidRegion neighbor : region.getAdjacentRegions()) {
                assertTrue(neighbor.getAdjacentRegions().contains(region),
                        region + " adjacency with " + neighbor + " is not symmetric");
            }
        }
    }

    @Test
    public void adjacency_isNonNullAndValid() {
        for (ValidRegion region : ValidRegion.values()) {
            List<ValidRegion> adj = region.getAdjacentRegions();
            assertNotNull(adj, region + " adjacency list should not be null");
            for (ValidRegion neighbor : adj) {
                assertNotNull(neighbor, "Neighbor should not be null");
            }
        }
    }

    @Test
    public void testSelfDistanceIsZero() {
        for (ValidRegion region : ValidRegion.values()) {
            assertEquals(0, ValidRegion.getDistance(region, region),
                    "Distance to itself should be zero for " + region.name());
        }
    }

    @Test
    public void testDistanceSymmetry() {
        for (ValidRegion a : ValidRegion.values()) {
            for (ValidRegion b : ValidRegion.values()) {
                assertEquals(ValidRegion.getDistance(a, b), ValidRegion.getDistance(b, a),
                        "Distances should be symmetric between " + a + " and " + b);
            }
        }
    }

    @Test
    public void testRegionDistances() {
        assertEquals(1, ValidRegion.getDistance(ValidRegion.BEDOK, ValidRegion.TAMPINES),
                "Bedok ↔ Tampines should be 1 hop apart");

        assertEquals(2, ValidRegion.getDistance(ValidRegion.WOODLANDS, ValidRegion.YISHUN),
                "Woodlands ↔ Yishun should be 2 hops apart");
        assertEquals(2, ValidRegion.getDistance(ValidRegion.QUEENSTOWN, ValidRegion.NOVENA),
                "Queenstown ↔ Novena should be 2 hops apart");

        assertEquals(5, ValidRegion.getDistance(ValidRegion.WOODLANDS, ValidRegion.TOA_PAYOH),
                "Woodlands ↔ Toa Payoh should be 5 hops apart");

        assertEquals(6, ValidRegion.getDistance(ValidRegion.WOODLANDS, ValidRegion.TAMPINES),
                "Woodlands ↔ Tampines should be roughly 6 hops apart");
    }
}
