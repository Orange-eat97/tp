package seedu.address.model.person;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Enum representing Singapore's valid regions. Each region stores its name,
 * main region and a list of adjacent regions.
 */
public enum ValidRegion {
    WOODLANDS("Woodlands", "North"),
    YISHUN("Yishun", "North"),
    SEMBAWANG("Sembawang", "North"),
    MANDAI("Mandai", "North"),
    SUNGEI_KADUT("Sungei Kadut", "North"),

    // --- North-East ---
    PUNGGOL("Punggol", "North-East"),
    SENGKANG("Sengkang", "North-East"),
    HOUGANG("Hougang", "North-East"),
    SELETAR("Seletar", "North-East"),
    SERANGOON("Serangoon", "North-East"),

    // --- East ---
    PASIR_RIS("Pasir Ris", "East"),
    TAMPINES("Tampines", "East"),
    BEDOK("Bedok", "East"),
    PAYA_LEBAR("Paya Lebar", "East"),
    CHANGI("Changi", "East"),

    // --- Central ---
    TOA_PAYOH("Toa Payoh", "Central"),
    BISHAN("Bishan", "Central"),
    ANG_MO_KIO("Ang Mo Kio", "Central"),
    NOVENA("Novena", "Central"),
    GEYLANG("Geylang", "Central"),
    MARINE_PARADE("Marine Parade", "Central"),
    KALLANG("Kallang", "Central"),
    QUEENSTOWN("Queenstown", "Central"),
    BUKIT_MERAH("Bukit Merah", "Central"),
    BUKIT_TIMAH("Bukit Timah", "Central"),
    TANGLIN("Tanglin", "Central"),
    RIVER_VALLEY("River Valley", "Central"),

    // --- West ---
    JURONG_WEST("Jurong West", "West"),
    JURONG_EAST("Jurong East", "West"),
    BOON_LAY("Boon Lay", "West"),
    CLEMENTI("Clementi", "West"),
    BUKIT_BATOK("Bukit Batok", "West"),
    BUKIT_PANJANG("Bukit Panjang", "West"),
    TUAS("Tuas", "West"),
    LIM_CHU_KANG("Lim Chu Kang", "West"),
    WESTERN_WATER_CATCHMENT("Western Water Catchment", "West"),
    TENGAH("Tengah", "West");

    private static final int N = values().length;
    // Static Distance matrix (computed once)
    private static final int[][] DIST_MATRIX = new int[N][N];

    private final String displayName;
    private final String mainRegion;
    private List<ValidRegion> adjacentRegions;

    ValidRegion(String displayName, String mainRegion) {
        this.displayName = displayName;
        this.mainRegion = mainRegion;
    }

    /**
     * Returns the display name of this region.
     *
     * @return the human-readable name of the region (e.g., "Woodlands")
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the main region group this area belongs to.
     *
     * @return the main region name (e.g., "North", "East", etc.)
     */
    public String getMainRegion() {
        return mainRegion;
    }

    /**
     * Returns an unmodifiable list of regions adjacent to this one.
     *
     * @return list of directly connected neighboring regions
     */
    public List<ValidRegion> getAdjacentRegions() {
        return Collections.unmodifiableList(adjacentRegions);
    }

    /**
     * Finds a region by its lowercase display name.
     *
     * @param name lowercase name of the region (e.g., "woodlands")
     * @return the corresponding {@code ValidRegion}, or {@code null} if not
     *          found
     */
    public static ValidRegion fromName(String name) {
        if (name == null) {
            return null;
        }

        for (ValidRegion region : values()) {
            if (region.displayName.equalsIgnoreCase(name)) {
                return region;
            }
        }
        return null;
    }

    /**
     * Checks if the given lowercase name corresponds to a valid region.
     *
     * @param name lowercase name of the region (e.g., "woodlands")
     * @return {@code true} if the name matches a valid region, {@code false}
     *      otherwise
     */
    public static boolean isValidRegion(String name) {
        if (name == null) {
            return false;
        }

        for (ValidRegion region : values()) {
            if (region.displayName.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the shortest distance between two regions.
     *
     * @param from the starting {@code ValidRegion}
     * @param to the destination {@code ValidRegion}
     * @return the shortest distance between the two regions, or
     *      {@code Integer.MAX_VALUE} if no path exists
     */
    public static int getDistance(ValidRegion from, ValidRegion to) {
        assert DIST_MATRIX != null : "Distance matrix not initialized. Run computeDistanceMatrix() first.";

        return DIST_MATRIX[from.ordinal()][to.ordinal()];
    }

    /**
     * Returns an array of all region display names.
     */
    public static String[] getAllRegionNames() {
        ValidRegion[] regions = values();
        String[] names = new String[regions.length];
        for (int i = 0; i < regions.length; i++) {
            names[i] = regions[i].toString(); // or regions[i].getDisplayName() if you have one
        }
        return names;
    }

    @Override
    public String toString() {
        return displayName;
    }

    /**
     * Sets adjacency relations between regions. This is called once in a static
     * initialization block.
     *
     * @param neighbors list of regions that are directly adjacent
     */
    private void setAdjacents(ValidRegion... neighbors) {
        this.adjacentRegions = Arrays.asList(neighbors);
    }

    /**
     * Initialises all adjacency relations. This is called once in a static
     * initialization block.
     */
    private static void initialiseAdjacencies() {
        Map<ValidRegion, Set<ValidRegion>> adjacencyMap = new HashMap<>();

        // Helper to add bidirectional adjacency
        BiConsumer<ValidRegion, ValidRegion> addAdjacency = (a, b) -> {
            adjacencyMap.computeIfAbsent(a, k -> new HashSet<>()).add(b);
            adjacencyMap.computeIfAbsent(b, k -> new HashSet<>()).add(a);
        };

        // --- North ---
        addAdjacency.accept(WOODLANDS, SEMBAWANG);
        addAdjacency.accept(WOODLANDS, SUNGEI_KADUT);
        addAdjacency.accept(YISHUN, SEMBAWANG);
        addAdjacency.accept(YISHUN, ANG_MO_KIO);
        addAdjacency.accept(YISHUN, MANDAI);
        addAdjacency.accept(YISHUN, SELETAR);
        addAdjacency.accept(SEMBAWANG, MANDAI);
        addAdjacency.accept(MANDAI, SUNGEI_KADUT);
        addAdjacency.accept(SUNGEI_KADUT, LIM_CHU_KANG);
        addAdjacency.accept(SUNGEI_KADUT, BUKIT_PANJANG);

        // --- North-East ---
        addAdjacency.accept(PUNGGOL, SENGKANG);
        addAdjacency.accept(PUNGGOL, PASIR_RIS);
        addAdjacency.accept(PUNGGOL, SELETAR);
        addAdjacency.accept(SENGKANG, HOUGANG);
        addAdjacency.accept(SENGKANG, SELETAR);
        addAdjacency.accept(HOUGANG, PAYA_LEBAR);
        addAdjacency.accept(HOUGANG, SERANGOON);
        addAdjacency.accept(HOUGANG, ANG_MO_KIO);
        addAdjacency.accept(SELETAR, ANG_MO_KIO);
        addAdjacency.accept(SERANGOON, BISHAN);
        addAdjacency.accept(SERANGOON, TOA_PAYOH);

        // --- East ---
        addAdjacency.accept(PASIR_RIS, TAMPINES);
        addAdjacency.accept(TAMPINES, BEDOK);
        addAdjacency.accept(TAMPINES, PAYA_LEBAR);
        addAdjacency.accept(TAMPINES, CHANGI);
        addAdjacency.accept(BEDOK, PAYA_LEBAR);
        addAdjacency.accept(BEDOK, MARINE_PARADE);
        addAdjacency.accept(PAYA_LEBAR, GEYLANG);
        addAdjacency.accept(PAYA_LEBAR, MARINE_PARADE);
        addAdjacency.accept(GEYLANG, MARINE_PARADE);

        // --- Central ---
        addAdjacency.accept(TOA_PAYOH, BISHAN);
        addAdjacency.accept(TOA_PAYOH, NOVENA);
        addAdjacency.accept(TOA_PAYOH, KALLANG);
        addAdjacency.accept(BISHAN, ANG_MO_KIO);
        addAdjacency.accept(BISHAN, BUKIT_TIMAH);
        addAdjacency.accept(ANG_MO_KIO, SELETAR);
        addAdjacency.accept(ANG_MO_KIO, YISHUN);
        addAdjacency.accept(NOVENA, GEYLANG);
        addAdjacency.accept(NOVENA, KALLANG);
        addAdjacency.accept(MARINE_PARADE, KALLANG);
        addAdjacency.accept(KALLANG, QUEENSTOWN);
        addAdjacency.accept(QUEENSTOWN, BUKIT_MERAH);
        addAdjacency.accept(QUEENSTOWN, BUKIT_TIMAH);
        addAdjacency.accept(BUKIT_MERAH, TANGLIN);
        addAdjacency.accept(BUKIT_TIMAH, CLEMENTI);
        addAdjacency.accept(BUKIT_TIMAH, BUKIT_BATOK);
        addAdjacency.accept(TANGLIN, RIVER_VALLEY);
        addAdjacency.accept(RIVER_VALLEY, BUKIT_MERAH);

        // --- West ---
        addAdjacency.accept(JURONG_WEST, JURONG_EAST);
        addAdjacency.accept(JURONG_WEST, BOON_LAY);
        addAdjacency.accept(JURONG_WEST, TUAS);
        addAdjacency.accept(BOON_LAY, TUAS);
        addAdjacency.accept(JURONG_EAST, CLEMENTI);
        addAdjacency.accept(JURONG_EAST, BUKIT_BATOK);
        addAdjacency.accept(JURONG_EAST, TENGAH);
        addAdjacency.accept(CLEMENTI, BUKIT_BATOK);
        addAdjacency.accept(BUKIT_BATOK, BUKIT_PANJANG);
        addAdjacency.accept(BUKIT_PANJANG, LIM_CHU_KANG);
        addAdjacency.accept(LIM_CHU_KANG, WESTERN_WATER_CATCHMENT);
        addAdjacency.accept(WESTERN_WATER_CATCHMENT, TUAS);

        // Assign sets to enum fields
        adjacencyMap.forEach((region, neighbors) -> region.setAdjacents(neighbors.toArray(new ValidRegion[0])));
    }

    /**
     * BFS to compute the shortest distances from a source region to all others.
     * This is called once for each region in a static initialization block.
     *
     * @param source the region to start the BFS from
     */
    private static void bfsComputeDistances(ValidRegion source) {
        int start = source.ordinal();
        Queue<ValidRegion> queue = new ArrayDeque<>();
        queue.add(source);

        boolean[] visited = new boolean[N];
        visited[start] = true;

        while (!queue.isEmpty()) {
            ValidRegion current = queue.poll();
            int currentIndex = current.ordinal();

            for (ValidRegion neighbor : current.adjacentRegions) {
                int neighborIndex = neighbor.ordinal();
                if (!visited[neighborIndex]) {
                    visited[neighborIndex] = true;
                    DIST_MATRIX[start][neighborIndex] = DIST_MATRIX[start][currentIndex] + 1;
                    queue.add(neighbor);
                }
            }
        }
    }

    /**
     * Assert that all region have adjacencies set
     * Should be called after all adjacents are initialized.
     */
    private static void assertAdjacencyInitialised() {
        for (ValidRegion region : values()) {
            assert region.adjacentRegions != null : "Region " + region.toString()
                    + " has no initialised adjacent regions!";
        }
    }

    /**
     * Assert that all region adjacencies are bidirectional.
     * Should be called after all adjacents are initialized.
     */
    private static void assertAdjacencySymmetry() {
        for (ValidRegion region : values()) {
            for (ValidRegion neighbor : region.adjacentRegions) {
                assert neighbor.adjacentRegions.contains(region) : "Lack of symmetry between " + region.toString()
                        + " and " + neighbor.toString() + ". Fix Adjacency List!";
            }
        }
    }

    static {
        // Initialise Adjacency lists
        initialiseAdjacencies();

        assertAdjacencyInitialised();
        assertAdjacencySymmetry();

        // Initialise distance matrix
        for (int i = 0; i < N; i++) {
            Arrays.fill(DIST_MATRIX[i], Integer.MAX_VALUE);
            DIST_MATRIX[i][i] = 0; // Distance to itself is 0
        }

        // Run BFS from each region to compute shortest distances
        for (ValidRegion source : values()) {
            bfsComputeDistances(source);
        }
    }
}
