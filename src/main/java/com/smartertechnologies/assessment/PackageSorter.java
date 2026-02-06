package com.smartertechnologies.assessment;

import java.util.Objects;

/**
 * Sorts packages into stacks based on dimensions (cm) and mass (kg).
 *
 * <p>Rules:
 * <ul>
 *   <li>Bulky if volume (w*h*l) >= {@link #BULKY_VOLUME_THRESHOLD_CM3}
 *       or any dimension >= {@link #BULKY_DIMENSION_THRESHOLD_CM}</li>
 *   <li>Heavy if mass >= {@link #HEAVY_MASS_THRESHOLD_KG}</li>
 *   <li>{@link #STACK_STANDARD} if not bulky and not heavy</li>
 *   <li>{@link #STACK_SPECIAL} if either bulky or heavy (but not both)</li>
 *   <li>{@link #STACK_REJECTED} if both bulky and heavy</li>
 * </ul>
 */
public final class PackageSorter {
    /**
     * Volume threshold in cm^3 at or above which a package is considered bulky.
     */
    public static final long BULKY_VOLUME_THRESHOLD_CM3 = 1_000_000L;

    /**
     * Single-dimension threshold in cm at or above which a package is considered bulky.
     */
    public static final long BULKY_DIMENSION_THRESHOLD_CM = 150L;

    /**
     * Mass threshold in kg at or above which a package is considered heavy.
     */
    public static final long HEAVY_MASS_THRESHOLD_KG = 20L;

    private PackageSorter() {
        // Utility class
    }

    public enum Stack {
        STANDARD,
        SPECIAL,
        REJECTED
    }

    /**
     * String representations of the three stacks. Prefer these over raw string literals.
     */
    public static final String STACK_STANDARD = Stack.STANDARD.name();
    public static final String STACK_SPECIAL = Stack.SPECIAL.name();
    public static final String STACK_REJECTED = Stack.REJECTED.name();

    /**
     * Dispatch a package to the correct stack.
     *
     * @param widthCm  width in centimeters, must be >= 0
     * @param heightCm height in centimeters, must be >= 0
     * @param lengthCm length in centimeters, must be >= 0
     * @param massKg   mass in kilograms, must be >= 0
     * @return stack name ("STANDARD", "SPECIAL", "REJECTED")
     * @throws IllegalArgumentException if any input is negative
     */
    public static String sort(long widthCm, long heightCm, long lengthCm, long massKg) {
        return sortStack(widthCm, heightCm, lengthCm, massKg).name();
    }

    /**
     * Same logic as {@link #sort(long, long, long, long)} but returns a strongly-typed enum.
     */
    public static Stack sortStack(long widthCm, long heightCm, long lengthCm, long massKg) {
        validateNonNegative("widthCm", widthCm);
        validateNonNegative("heightCm", heightCm);
        validateNonNegative("lengthCm", lengthCm);
        validateNonNegative("massKg", massKg);

        boolean bulky = isBulky(widthCm, heightCm, lengthCm);
        boolean heavy = massKg >= HEAVY_MASS_THRESHOLD_KG;

        if (bulky && heavy) return Stack.REJECTED;
        if (bulky || heavy) return Stack.SPECIAL;
        return Stack.STANDARD;
    }

    private static boolean isBulky(long widthCm, long heightCm, long lengthCm) {
        long maxDimension = Math.max(widthCm, Math.max(heightCm, lengthCm));
        if (maxDimension >= BULKY_DIMENSION_THRESHOLD_CM) return true;

        // Overflow-safe volume: if multiplication overflows, the true volume exceeds Long.MAX_VALUE,
        // which is definitely above the bulky threshold (1,000,000), so treat as bulky.
        long volume = safeMultiply(safeMultiply(widthCm, heightCm), lengthCm);
        return volume >= BULKY_VOLUME_THRESHOLD_CM3;
    }

    /**
     * Multiplies two non-negative longs; on overflow returns Long.MAX_VALUE.
     */
    private static long safeMultiply(long a, long b) {
        if (a == 0L || b == 0L) return 0L;
        if (a > Long.MAX_VALUE / b) return Long.MAX_VALUE;
        return a * b;
    }

    private static void validateNonNegative(String name, long value) {
        Objects.requireNonNull(name, "name");
        if (value < 0L) {
            throw new IllegalArgumentException(name + " must be >= 0, but was " + value);
        }
    }
}

