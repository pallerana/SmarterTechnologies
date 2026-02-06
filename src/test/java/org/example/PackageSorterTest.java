package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PackageSorterTest {

    @Test
    void standard_whenNotBulkyAndNotHeavy() {
        assertEquals(PackageSorter.STACK_STANDARD, PackageSorter.sort(10, 10, 10, 1));
    }

    @Test
    void standard_atZeroValues() {
        assertEquals(PackageSorter.STACK_STANDARD, PackageSorter.sort(0, 0, 0, 0));
    }

    @Test
    void standard_justBelowAllThresholds() {
        // Below heavy mass threshold and below bulky volume threshold.
        long belowMass = PackageSorter.HEAVY_MASS_THRESHOLD_KG - 1;
        long w = 99, h = 100, l = 100; // 99 * 100 * 100 = 990_000 < 1_000_000
        assertEquals(PackageSorter.STACK_STANDARD, PackageSorter.sort(w, h, l, belowMass));
    }

    @Test
    void special_whenHeavyOnly_massAtThreshold() {
        assertEquals(PackageSorter.STACK_SPECIAL,
                PackageSorter.sort(10, 10, 10, PackageSorter.HEAVY_MASS_THRESHOLD_KG));
    }

    @Test
    void special_whenHeavyOnly_strictlyAboveThreshold() {
        long aboveMass = PackageSorter.HEAVY_MASS_THRESHOLD_KG + 1;
        assertEquals(PackageSorter.STACK_SPECIAL, PackageSorter.sort(10, 10, 10, aboveMass));
    }

    @Test
    void special_whenBulkyOnly_byVolumeAtThreshold() {
        // 100 * 100 * 100 = 1,000,000 cm^3
        assertEquals(PackageSorter.STACK_SPECIAL,
                PackageSorter.sort(100, 100, 100, 1));
    }

    @Test
    void special_whenBulkyOnly_byVolumeJustAboveThreshold() {
        long v = PackageSorter.BULKY_VOLUME_THRESHOLD_CM3 + 1;
        // Simple factorization: 10 * 10 * (threshold/100 + 1)
        long w = 10, h = 10;
        long l = (PackageSorter.BULKY_VOLUME_THRESHOLD_CM3 / (w * h)) + 1;
        assertEquals(PackageSorter.STACK_SPECIAL, PackageSorter.sort(w, h, l, 1));
    }

    @Test
    void special_whenBulkyOnly_byDimensionAtThreshold() {
        long d = PackageSorter.BULKY_DIMENSION_THRESHOLD_CM;
        assertEquals(PackageSorter.STACK_SPECIAL, PackageSorter.sort(d, 1, 1, 1));
        assertEquals(PackageSorter.STACK_SPECIAL, PackageSorter.sort(1, d, 1, 1));
        assertEquals(PackageSorter.STACK_SPECIAL, PackageSorter.sort(1, 1, d, 1));
    }

    @Test
    void special_whenBulkyOnly_byDimensionJustAboveThreshold() {
        long d = PackageSorter.BULKY_DIMENSION_THRESHOLD_CM + 1;
        assertEquals(PackageSorter.STACK_SPECIAL, PackageSorter.sort(d, 1, 1, 1));
    }

    @Test
    void rejected_whenBulkyAndHeavy() {
        assertEquals(PackageSorter.STACK_REJECTED,
                PackageSorter.sort(PackageSorter.BULKY_DIMENSION_THRESHOLD_CM, 10, 10,
                        PackageSorter.HEAVY_MASS_THRESHOLD_KG));
        assertEquals(PackageSorter.STACK_REJECTED,
                PackageSorter.sort(100, 100, 100, PackageSorter.HEAVY_MASS_THRESHOLD_KG));
    }

    @Test
    void treatsOverflowingVolumeAsBulky() {
        // Force overflow: Long.MAX_VALUE * 2 overflows, safeMultiply saturates to Long.MAX_VALUE.
        assertEquals(PackageSorter.STACK_SPECIAL, PackageSorter.sort(Long.MAX_VALUE, 2, 2, 1));
    }

    @Test
    void negativeInputsRejected() {
        assertThrows(IllegalArgumentException.class, () -> PackageSorter.sort(-1, 1, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> PackageSorter.sort(1, -1, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> PackageSorter.sort(1, 1, -1, 1));
        assertThrows(IllegalArgumentException.class, () -> PackageSorter.sort(1, 1, 1, -1));
    }
}

