package ch.supsi.memory.backend.business.validate;

public class GridCoordinatesRule {

    public static final int MIN_X = 0;
    public static final int MIN_Y = 0;
    public static final int MAX_X = 5;
    public static final int MAX_Y = 7;

    public static boolean isValid(int[] coords) {
        final boolean insideXDomain = coords[0] >= MIN_X && coords[0] <= MAX_X;
        final boolean insideYDomain = coords[1] >= MIN_Y && coords[1] <= MAX_Y;

        return insideXDomain && insideYDomain;
    }
}
