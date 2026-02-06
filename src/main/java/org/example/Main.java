package org.example;

public class Main {
    public static void main(String[] args) {
        // Small demo; the core deliverable is PackageSorter.sort(...).
        System.out.println(PackageSorter.sort(10, 10, 10, 1));    // STANDARD
        System.out.println(PackageSorter.sort(100, 100, 100, 1)); // SPECIAL (bulky by volume)
        System.out.println(PackageSorter.sort(10, 10, 10, 20));   // SPECIAL (heavy)
        System.out.println(PackageSorter.sort(150, 10, 10, 20));  // REJECTED (bulky + heavy)
    }
}