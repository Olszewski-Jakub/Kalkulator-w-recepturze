package com.jakubolszewski.kalkulatorwrecepturzedoz.Calcualtions

import android.util.Log
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitAModel
import kotlin.math.round

private var TAG: String = "VitaminACalculations"


class VitaminACalculations(
    val company: Int,
    val units: Int,
    var amount: Double,
    val vitAList: ArrayList<VitAModel>
) {


    lateinit var vitModel1: VitaminAGridModel
    lateinit var vitModel2: VitaminAGridModel
    lateinit var vitModel3: VitaminAGridModel

    // Function to calculate Hasco
    private fun Hasco(): Map<String, VitaminAGridModel> {
        val density = vitAList.get(0).density
        val mass_units = vitAList.get(0).mass_units
        val drops = vitAList.get(0).drops


        fun grams() {
            var volumeHasco = amount / density
            volumeHasco = round(volumeHasco * 100) / 100

            val dropsHasco = round(volumeHasco * drops)
            var massUnitHasco = volumeHasco * mass_units
            massUnitHasco = round(massUnitHasco)
            val massUnit_String: String = java.lang.String.valueOf(massUnitHasco).replace(".0", "")

            vitModel1 = VitaminAGridModel(
                main_vit = "Hasco",
                main_vit2 = density.toString(),
                mass = amount.toString(),
                volume = volumeHasco.toString(),
                drops = dropsHasco.toString(),
                massunits = massUnit_String,
                howMuchTosell = ""
            )

        }

        fun mass_unit() {
            var volumeHasco = amount / mass_units
            volumeHasco = round(volumeHasco * 100) / 100
            val dropsHasco = round(volumeHasco * drops)
            val gramsHasco = round(volumeHasco * density * 100) / 100

            vitModel1 = VitaminAGridModel(
                main_vit = "Hasco",
                main_vit2 = density.toString(),
                mass = gramsHasco.toString(),
                volume = volumeHasco.toString(),
                drops = dropsHasco.toString(),
                massunits = amount.toString(),
                howMuchTosell = ""
            )

        }

        fun mililiters() {
            val massUnitHasco = round(amount * mass_units)
            val dropsHasco = round(amount * drops)
            val gramsHasco = round(amount * density * 100) / 100
            vitModel1 = VitaminAGridModel(
                main_vit = "Hasco",
                main_vit2 = density.toString(),
                mass = gramsHasco.toString(),
                volume = amount.toString(),
                drops = dropsHasco.toString(),
                massunits = massUnitHasco.toString(),
                howMuchTosell = ""
            )
        }

        when (units) {
            0 -> grams()
            1 -> mass_unit()
            2 -> mililiters()
            else -> {
                Log.d(TAG, "Unit Id outside of range. Couldn't perform calculations")
            }
        }

        //Hasco na Medana
        val massUnitMedana = vitModel1.massunits.toDouble()
        val volumeMedna = round(massUnitMedana / vitAList[1].mass_units * 100) / 100
        val dropsMedana = round(volumeMedna * vitAList[1].drops)
        val gramsMedana = round(volumeMedna * vitAList[1].density * 100) / 100
        vitModel2 = VitaminAGridModel(
            main_vit = "Medana",
            main_vit2 = vitAList[1].density.toString(),
            mass = gramsMedana.toString(),
            volume = volumeMedna.toString(),
            drops = dropsMedana.toString(),
            massunits = massUnitMedana.toString(),
            howMuchTosell = ""
        )

        //Hasco na Fagron
        val massUnitFagron = vitModel1.massunits.toDouble()
        val dropsFagron = massUnitFagron / vitAList[2].drops
        val gramsFagron = round(dropsFagron * vitAList[2].mass_units * 100) / 100
        val volumeFagron = 0

        vitModel3 = VitaminAGridModel(
            main_vit = "Fagron",
            main_vit2 = vitAList[2].density.toString(),
            mass = gramsFagron.toString(),
            volume = volumeFagron.toString(),
            drops = dropsFagron.toString(),
            massunits = massUnitFagron.toString(),
            howMuchTosell = ""
        )

        //return map of 3 vitA models
        return mapOf("Vit1" to vitModel1, "Vit2" to vitModel2, "Vit3" to vitModel3)
    }

    // Function to calculate Medana
    private fun Medana(): Map<String, VitaminAGridModel> {
        val density = vitAList.get(1).density
        val mass_units = vitAList.get(1).mass_units
        val drops = vitAList.get(1).drops

        fun grams() {
            var volumeMedana = amount / density
            volumeMedana = round(volumeMedana * 100) / 100

            val dropsMedana = round(volumeMedana * drops)
            var massUnitMedana = volumeMedana * mass_units
            massUnitMedana = round(massUnitMedana)
            val massUnit_String: String = java.lang.String.valueOf(massUnitMedana).replace(".0", "")

            vitModel1 = VitaminAGridModel(
                main_vit = "Medana",
                main_vit2 = density.toString(),
                mass = amount.toString(),
                volume = volumeMedana.toString(),
                drops = dropsMedana.toString(),
                massunits = massUnit_String,
                howMuchTosell = ""
            )

        }

        fun mass_unit() {
            var volumeMedana = amount / mass_units
            volumeMedana = round(volumeMedana * 100) / 100
            val dropsMedana = round(volumeMedana * drops)
            val gramsMedana = round(volumeMedana * density * 100) / 100

            vitModel1 = VitaminAGridModel(
                main_vit = "Medana",
                main_vit2 = density.toString(),
                mass = gramsMedana.toString(),
                volume = volumeMedana.toString(),
                drops = dropsMedana.toString(),
                massunits = amount.toString(),
                howMuchTosell = ""
            )

        }

        fun mililiters() {
            val massUnitMedana = round(amount * mass_units)
            val dropsMedana = round(amount * drops)
            val gramsMedana = round(amount * density * 100) / 100
            vitModel1 = VitaminAGridModel(
                main_vit = "Medana",
                main_vit2 = density.toString(),
                mass = gramsMedana.toString(),
                volume = amount.toString(),
                drops = dropsMedana.toString(),
                massunits = massUnitMedana.toString(),
                howMuchTosell = ""
            )
        }
        /*
        * Selecting unit for calculation(user input)
        */
        when (units) {
            0 -> grams()
            1 -> mass_unit()
            2 -> mililiters()
            else -> {
                Log.d(TAG, "Unit Id outside of range. Couldn't perform calculations")
            }
        }
        val massUnitHasco = vitModel1.massunits.toDouble()
        val volumeHasco = round(massUnitHasco / vitAList[0].mass_units * 100) / 100
        val dropsHasco = round(volumeHasco * vitAList[0].drops)
        val gramsHasco = round(volumeHasco * vitAList[0].density * 100) / 100
        vitModel2 = VitaminAGridModel(
            main_vit = "Hasco",
            main_vit2 = vitAList[0].density.toString(),
            mass = gramsHasco.toString(),
            volume = volumeHasco.toString(),
            drops = dropsHasco.toString(),
            massunits = massUnitHasco.toString(),
            howMuchTosell = ""
        )

        //Hasco na Fagron
        val massUnitFagron = vitModel1.massunits.toDouble()
        val dropsFagron = massUnitFagron / vitAList[2].drops
        val gramsFagron = round(dropsFagron * vitAList[2].mass_units * 100) / 100
        val volumeFagron = 0

        vitModel3 = VitaminAGridModel(
            main_vit = "Fagron",
            main_vit2 = vitAList[2].density.toString(),
            mass = gramsFagron.toString(),
            volume = volumeFagron.toString(),
            drops = dropsFagron.toString(),
            massunits = massUnitFagron.toString(),
            howMuchTosell = ""
        )
        //return map of 3 vitA models
        return mapOf("Vit1" to vitModel1, "Vit2" to vitModel2, "Vit3" to vitModel3)
    }

    // Function to calculate Fagron
    private fun Fagron(): Map<String, VitaminAGridModel> {

        val density = vitAList.get(2).density
        val mass_units = vitAList.get(2).mass_units
        val drops = vitAList.get(2).drops
        fun mass_unit() {

            var dropsFagron = round(amount / drops)
            var gramsFagron = round(dropsFagron * mass_units * 100) / 100
            vitModel1 = VitaminAGridModel(
                main_vit = "Fagron",
                main_vit2 = vitAList[2].density.toString(),
                mass = gramsFagron.toString(),
                volume = "",
                drops = dropsFagron.toString(),
                massunits = amount.toString(),
                howMuchTosell = ""
            )

            val massUnitHasco = vitModel1.massunits.toDouble()
            val volumeHasco = round(massUnitHasco / vitAList[0].mass_units * 100) / 100
            val dropsHasco = round(volumeHasco * vitAList[0].drops)
            val gramsHasco = round(volumeHasco * vitAList[0].density * 100) / 100

            vitModel2 = VitaminAGridModel(
                main_vit = "Hasco",
                main_vit2 = vitAList[0].density.toString(),
                mass = gramsHasco.toString(),
                volume = volumeHasco.toString(),
                drops = dropsHasco.toString(),
                massunits = massUnitHasco.toString(),
                howMuchTosell = ""
            )

            val massUnitMedana = vitModel1.massunits.toDouble()
            val volumeMedna = round(massUnitMedana / vitAList[1].mass_units * 100) / 100
            val dropsMedana = round(volumeMedna * vitAList[1].drops)
            val gramsMedana = round(volumeMedna * vitAList[1].density * 100) / 100
            vitModel3 = VitaminAGridModel(
                main_vit = "Medana",
                main_vit2 = vitAList[1].density.toString(),
                mass = gramsMedana.toString(),
                volume = volumeMedna.toString(),
                drops = dropsMedana.toString(),
                massunits = massUnitMedana.toString(),
                howMuchTosell = ""
            )
        }
        vitModel1 = VitaminAGridModel(
            main_vit = "Zmień jednonstkę",
            main_vit2 = "j.m",
            mass = "Null",
            volume = "Null",
            drops = "Null",
            massunits = "Null",
            howMuchTosell = "Null"
        )
        vitModel2 = VitaminAGridModel(
            main_vit = "Zmień jednonstkę",
            main_vit2 = "j.m",
            mass = "Null",
            volume = "Null",
            drops = "Null",
            massunits = "Null",
            howMuchTosell = "Null"
        )
        vitModel3 = VitaminAGridModel(
            main_vit = "Zmień jednonstkę",
            main_vit2 = "j.m",
            mass = "Null",
            volume = "Null",
            drops = "Null",
            massunits = "Null",
            howMuchTosell = "Null"
        )

        when (units) {
            1 -> mass_unit()
            else -> {
                Log.d(TAG, "Unit Id outside of range. Couldn't perform calculations")
            }

        }
        //return map of 3 vitA models
        return mapOf("Vit1" to vitModel1, "Vit2" to vitModel2, "Vit3" to vitModel3)

    }

    fun calculate(): Map<String, VitaminAGridModel> {
        //Selecting company (user input)
        when (company) {
            0 -> return Hasco()
            1 -> return Medana()
            2 -> return Fagron()
            else -> {
                Log.d(TAG, "Company Id outside of range. Couldn't perform calculations")
            }
        }
        //Return error Map of VitAGridModels to be used in the grid
        return mapOf(
            "Vit1" to VitaminAGridModel(
                main_vit = "Error",
                main_vit2 = "occurred",
                mass = "Null",
                volume = "Null",
                drops = "Null",
                massunits = "Null",
                howMuchTosell = "Null"
            )
        )
    }

}