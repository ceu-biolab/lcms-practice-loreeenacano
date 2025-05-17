package main;

import lipid.*;
import adduct.AdductList;

import java.util.Set;

public class Main {

    public static void main(String[] args) {
        int ppmTolerance = 10;

        System.out.println("### TEST 1: [M+Na]+ and [M+H]+ ###");
        runTest(
                new Peak(700.500, 100000),                       // [M+H]+
                new Peak(700.500 + 21.981942, 90000),            // [M+Na]+ = [M+H]+ + diff
                "PC 34:1", "C42H82NO8P", "PC", 34, 1, ppmTolerance);

        System.out.println("\n### TEST 2: [M+NH4]+ and [M+H]+ ###");
        runTest(
                new Peak(700.500, 100000),                       // [M+H]+
                new Peak(700.500 + 17.025, 95000),               // [M+NH4]+
                "PE 36:2", "C41H78NO8P", "PE", 36, 2, ppmTolerance);

        System.out.println("\n### TEST 3: [M+2H]2+ and [M+H]+ ###");
        runTest(
                new Peak(700.500, 100000),                       // [M+H]+
                new Peak((699.4927 + 2.0146) / 2, 85000),        // [M+2H]2+
                "TG 54:3", "C57H104O6", "TG", 54, 3, ppmTolerance);

        System.out.println("\n### TEST 4: [M+H-H2O]+ and [M+H]+ ###");
        runTest(
                new Peak(700.500, 100000),                       // [M+H]+
                new Peak(700.500 - 18.0106, 80000),              // [M+H–H₂O]+
                "PE 36:1", "C41H80NO8P", "PE", 36, 1, ppmTolerance);
    }

    private static void runTest(Peak p1, Peak p2, String name, String formula, String type, int carbons, int doubleBonds, int ppmTolerance) {
        Lipid lipid = new Lipid(999, name, formula, type, carbons, doubleBonds);
        Annotation annotation = new Annotation(
                lipid,
                p1.getMz(),  // referencia
                p1.getIntensity(),
                6.5,
                IonizationMode.POSITIVE,
                Set.of(p1, p2)
        );

        annotation.detectAdduct(ppmTolerance);

        System.out.printf("Grouped Peaks: %s, %s\n", p1, p2);
        System.out.printf("Detected adduct: %s\n", annotation.getAdduct());
    }
}
