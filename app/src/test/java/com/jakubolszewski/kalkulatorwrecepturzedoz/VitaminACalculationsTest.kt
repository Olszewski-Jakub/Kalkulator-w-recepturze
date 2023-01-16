package com.jakubolszewski.kalkulatorwrecepturzedoz

import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.Calcualtions.VitaminACalculations
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.DBHelper
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.VitAModel
import org.junit.Test
import org.junit.Assert.*


class VitaminACalculationsTest {
    var vitAList: ArrayList<VitAModel> = ArrayList()

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
}