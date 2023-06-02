package com.jakubolszewski.kalkulatorwrecepturzedoz

import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.calculations.VitaminACalculations
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitAModel
import org.junit.Test
import org.junit.Assert.*


class VitaminACalculationsTest {
    // Unit test for VitaminACalculations class

    // Test data
    var vitAList: ArrayList<VitAModel> = ArrayList()

    // Test 1 Hasco grams
    @Test
    internal fun Test_0_0() {
        vitAList.add(
            VitAModel(
                id = 0,
                company = "hasco",
                density = 1.148,
                drops = 28,
                mass_units = 45000.0
            )
        )
        vitAList.add(
            VitAModel(
                id = 1,
                company = "medana",
                density = 1.08,
                drops = 30,
                mass_units = 50000.0
            )
        )
        vitAList.add(
            VitAModel(
                id = 2,
                company = "fagron",
                density = 0.0,
                drops = 10000,
                mass_units = 0.034
            )
        )


        val VitaminACalculations = VitaminACalculations(0, 0, 10.0, vitAList).calculate()
        val a: VitaminAGridModel = VitaminAGridModel(
            main_vit = "Hasco",
            main_vit2 = "1.148",
            mass = "10.0",
            volume = "8.71",
            drops = "244.0",
            massunits = "391950",
            howMuchTosell = ""
        )
        assertEquals(a, VitaminACalculations.get("Vit1"))

    }

    // Test 2 Hasco MASS UNITS
    @Test
    internal fun Test_0_1() {
        vitAList.add(
            VitAModel(
                id = 0,
                company = "hasco",
                density = 1.148,
                drops = 28,
                mass_units = 45000.0
            )
        )
        vitAList.add(
            VitAModel(
                id = 1,
                company = "medana",
                density = 1.08,
                drops = 30,
                mass_units = 50000.0
            )
        )
        vitAList.add(
            VitAModel(
                id = 2,
                company = "fagron",
                density = 0.0,
                drops = 10000,
                mass_units = 0.034
            )
        )


        val VitaminACalculations = VitaminACalculations(0, 1, 100000.0, vitAList).calculate()
        val a: VitaminAGridModel = VitaminAGridModel(
            main_vit = "Hasco",
            main_vit2 = "1.148",
            mass = "2.55",
            volume = "2.22",
            drops = "62.0",
            massunits = "100000.0",
            howMuchTosell = ""
        )
        assertEquals(a, VitaminACalculations.get("Vit1"))

    }

    // Test 3 Hasco DROPS
    @Test
    internal fun Test_0_2() {
        vitAList.add(
            VitAModel(
                id = 0,
                company = "hasco",
                density = 1.148,
                drops = 28,
                mass_units = 45000.0
            )
        )
        vitAList.add(
            VitAModel(
                id = 1,
                company = "medana",
                density = 1.08,
                drops = 30,
                mass_units = 50000.0
            )
        )
        vitAList.add(
            VitAModel(
                id = 2,
                company = "fagron",
                density = 0.0,
                drops = 10000,
                mass_units = 0.034
            )
        )


        val VitaminACalculations = VitaminACalculations(0, 2, 10.0, vitAList).calculate()
        val a: VitaminAGridModel = VitaminAGridModel(
            main_vit = "Hasco",
            main_vit2 = "1.148",
            mass = "11.48",
            volume = "10.0",
            drops = "280.0",
            massunits = "450000.0",
            howMuchTosell = ""
        )
        assertEquals(a, VitaminACalculations.get("Vit1"))

    }

    //Test 4 Medana grams
    @Test
    internal fun Test_1_0() {
        vitAList.add(
            VitAModel(
                id = 0,
                company = "hasco",
                density = 1.148,
                drops = 28,
                mass_units = 45000.0
            )
        )
        vitAList.add(
            VitAModel(
                id = 1,
                company = "medana",
                density = 1.08,
                drops = 30,
                mass_units = 50000.0
            )
        )
        vitAList.add(
            VitAModel(
                id = 2,
                company = "fagron",
                density = 0.0,
                drops = 10000,
                mass_units = 0.034
            )
        )
        val VitaminACalculations = VitaminACalculations(1, 0, 10.0, vitAList).calculate()
        val a: VitaminAGridModel = VitaminAGridModel(
            main_vit = "Medana",
            main_vit2 = "1.08",
            mass = "10.0",
            volume = "9.26",
            drops = "278.0",
            massunits = "463000",
            howMuchTosell = ""
        )
        assertEquals(a, VitaminACalculations.get("Vit1"))

    }

    // Test 5 Medana MASS UNITS
    @Test
    internal fun Test_1_1() {
        vitAList.add(
            VitAModel(
                id = 0,
                company = "hasco",
                density = 1.148,
                drops = 28,
                mass_units = 45000.0
            )
        )
        vitAList.add(
            VitAModel(
                id = 1,
                company = "medana",
                density = 1.08,
                drops = 30,
                mass_units = 50000.0
            )
        )
        vitAList.add(
            VitAModel(
                id = 2,
                company = "fagron",
                density = 0.0,
                drops = 10000,
                mass_units = 0.034
            )
        )
        val VitaminACalculations = VitaminACalculations(1, 1, 463000.0, vitAList).calculate()
        val a: VitaminAGridModel = VitaminAGridModel(
            main_vit = "Medana",
            main_vit2 = "1.08",
            mass = "10.0",
            volume = "9.26",
            drops = "278.0",
            massunits = "463000.0",
            howMuchTosell = ""
        )
        assertEquals(a, VitaminACalculations.get("Vit1"))
    }

    // Test 6 Medana DROPS
    @Test
    internal fun Test_1_2() {
        vitAList.add(
            VitAModel(
                id = 0,
                company = "hasco",
                density = 1.148,
                drops = 28,
                mass_units = 45000.0
            )
        )
        vitAList.add(
            VitAModel(
                id = 1,
                company = "medana",
                density = 1.08,
                drops = 30,
                mass_units = 50000.0
            )
        )
        vitAList.add(
            VitAModel(
                id = 2,
                company = "fagron",
                density = 0.0,
                drops = 10000,
                mass_units = 0.034
            )
        )
        val VitaminACalculations = VitaminACalculations(1, 2, 10.0, vitAList).calculate()
        val a: VitaminAGridModel = VitaminAGridModel(
            main_vit = "Medana",
            main_vit2 = "1.08",
            mass = "10.8",
            volume = "10.0",
            drops = "300.0",
            massunits = "500000.0",
            howMuchTosell = ""
        )
        assertEquals(a, VitaminACalculations.get("Vit1"))
    }

    // Test 7 Fagron Mass units
    @Test
    internal fun Test_2_1() {
        vitAList.add(
            VitAModel(
                id = 0,
                company = "hasco",
                density = 1.148,
                drops = 28,
                mass_units = 45000.0
            )
        )
        vitAList.add(
            VitAModel(
                id = 1,
                company = "medana",
                density = 1.08,
                drops = 30,
                mass_units = 50000.0
            )
        )
        vitAList.add(
            VitAModel(
                id = 2,
                company = "fagron",
                density = 0.0,
                drops = 10000,
                mass_units = 0.034
            )
        )
        val VitaminACalculations = VitaminACalculations(2, 1, 1000000.0, vitAList).calculate()
        val a: VitaminAGridModel = VitaminAGridModel(
            main_vit = "Fagron",
            main_vit2 = "0.0",
            mass = "3.4",
            volume = "",
            drops = "100.0",
            massunits = "1000000.0",
            howMuchTosell = ""
        )
        assertEquals(a, VitaminACalculations.get("Vit1"))
    }
}