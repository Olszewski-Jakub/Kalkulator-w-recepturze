package com.jakubolszewski.kalkulatorwrecepturzedoz.Calcualtions

import android.util.Log
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.VitAModel
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

    private fun Hasco(): Map<String, VitaminAGridModel> {
        val density = vitAList.get(0).density
        val mass_units = vitAList.get(0).mass_units
        val drops = vitAList.get(0).drops


        fun grams() {
            var volumeHasco = amount / density
            volumeHasco = round(volumeHasco * 100) / 100

            val dropsHasco = round(volumeHasco * 28)
            var massUnitHasco = volumeHasco * 45000
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
        val gramsFagron = round(dropsFagron * vitAList[2].mass_units * 100) /100
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


        return mapOf("Vit1" to vitModel1, "Vit2" to vitModel2, "Vit3" to vitModel3)
    }

    private fun Medana(): Map<String, VitaminAGridModel> {


        return mapOf("Vit1" to vitModel1, "Vit2" to vitModel2, "Vit3" to vitModel3)
    }

    private fun Fagron(): Map<String, VitaminAGridModel> {
        val values = hashMapOf<String, Double>()



        return mapOf("Vit1" to vitModel1, "Vit2" to vitModel2, "Vit3" to vitModel3)
    }

    fun calculate(): Map<String, VitaminAGridModel> {

        when (company) {
            0 -> return Hasco()
            1 -> return Medana()
            2 -> return Fagron()
            else -> {
                Log.d(TAG, "Company Id outside of range. Couldn't perform calculations")
            }
        }

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