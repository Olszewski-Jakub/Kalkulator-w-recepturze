package com.jakubolszewski.kalkulatorwrecepturzedoz.calculations

import android.util.Log
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminEGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitEModel
import kotlin.math.roundToInt

class VitaminECalculation(
    val company: Int,
    val units: Int,
    var amount: Double,
    val vitEList: ArrayList<VitEModel>
) {

    private lateinit var vitModel1: VitaminEGridModel
    private lateinit var vitModel2: VitaminEGridModel
    private lateinit var vitModel3: VitaminEGridModel

    private fun Hasco(): Map<String, VitaminEGridModel> {
        fun grams() {
            val density = vitEList[0].density
            val drops = vitEList[0].drops

            val gramsHasco = amount
            var volumeHasco = gramsHasco / density
            volumeHasco = Math.round(volumeHasco * 100.0) / 100.0
            var dropsHasco = volumeHasco * drops
            dropsHasco = dropsHasco.roundToInt().toDouble()
            var howMuchToSell_hasco = volumeHasco / 10.0
            howMuchToSell_hasco = Math.round(howMuchToSell_hasco * 1000.0) / 1000.0
            vitModel1 = VitaminEGridModel(
                main_vit = "Hasco",
                main_vit2 = density.toString(),
                mass = gramsHasco.toString(),
                volume = volumeHasco.toString(),
                drops = dropsHasco.toString(),
                howMuchTosell = howMuchToSell_hasco.toString()
            )


            val gramsMedana = amount
            var volumeMedana = gramsMedana / vitEList[1].density
            volumeMedana = Math.round(volumeMedana * 100.0) / 100.0
            var dropsMedana = volumeMedana * vitEList[1].drops
            dropsMedana = dropsMedana.roundToInt().toDouble()
            var howMuchToSell_medana = volumeMedana / 10.0
            howMuchToSell_medana = Math.round(howMuchToSell_medana * 1000.0) / 1000.0
            vitModel2 = VitaminEGridModel(
                main_vit = "Medana",
                main_vit2 = vitEList[1].density.toString(),
                mass = gramsMedana.toString(),
                volume = volumeMedana.toString(),
                drops = dropsMedana.toString(),
                howMuchTosell = howMuchToSell_medana.toString()
            )

            var gramsFagron = volumeMedana * 0.3
            gramsFagron = Math.round(gramsFagron * 100.0) / 100.0
            var volumeFagron = gramsFagron / vitEList[2].density
            volumeFagron = Math.round(volumeFagron * 100.0) / 100.0
            var dropsFagron = gramsFagron / vitEList[2].drops
            dropsFagron = dropsFagron.roundToInt().toDouble()
            var howMuchToSell_fagron = volumeFagron / 10.0
            howMuchToSell_fagron = Math.round(howMuchToSell_fagron * 1000.0) / 1000.0
            vitModel3 = VitaminEGridModel(
                main_vit = "Fagron",
                main_vit2 = vitEList[2].density.toString(),
                mass = gramsFagron.toString(),
                volume = volumeFagron.toString(),
                drops = dropsFagron.toString(),
                howMuchTosell = howMuchToSell_fagron.toString()
            )
        }

        fun milliliters() {
            val volumeHasco = amount
            var gramsHasco = volumeHasco * vitEList[1].density
            gramsHasco = Math.round(gramsHasco * 100.0) / 100.0
            var dropsHasco = volumeHasco * vitEList[1].drops
            dropsHasco = dropsHasco.roundToInt().toDouble()
            var howMuchToSell_hasco = volumeHasco / 10.0
            howMuchToSell_hasco = Math.round(howMuchToSell_hasco * 1000.0) / 1000.0
            vitModel2 = VitaminEGridModel(
                main_vit = "Medana",
                main_vit2 = vitEList[1].density.toString(),
                mass = gramsHasco.toString(),
                volume = volumeHasco.toString(),
                drops = dropsHasco.toString(),
                howMuchTosell = howMuchToSell_hasco.toString()
            )

            val gramsMedana = amount
            var volumeMedana = gramsMedana / vitEList[1].density
            volumeMedana = Math.round(volumeMedana * 100.0) / 100.0
            var dropsMedana = volumeMedana * vitEList[1].drops
            dropsMedana = dropsMedana.roundToInt().toDouble()
            var howMuchToSell_medana = volumeMedana / 10.0
            howMuchToSell_medana = Math.round(howMuchToSell_medana * 1000.0) / 1000.0
            vitModel2 = VitaminEGridModel(
                main_vit = "Medana",
                main_vit2 = vitEList[1].density.toString(),
                mass = gramsMedana.toString(),
                volume = volumeMedana.toString(),
                drops = dropsMedana.toString(),
                howMuchTosell = howMuchToSell_medana.toString()
            )

            var gramsFagron = volumeMedana * 0.3
            gramsFagron = Math.round(gramsFagron * 100.0) / 100.0
            var volumeFagron = gramsFagron / vitEList[2].density
            volumeFagron = Math.round(volumeFagron * 100.0) / 100.0
            var dropsFagron = gramsFagron / vitEList[2].drops
            dropsFagron = dropsFagron.roundToInt().toDouble()
            var howMuchToSell_fagron = volumeFagron / 10.0
            howMuchToSell_fagron = Math.round(howMuchToSell_fagron * 1000.0) / 1000.0
            vitModel3 = VitaminEGridModel(
                main_vit = "Fagron",
                main_vit2 = vitEList[2].density.toString(),
                mass = gramsFagron.toString(),
                volume = volumeFagron.toString(),
                drops = dropsFagron.toString(),
                howMuchTosell = howMuchToSell_fagron.toString()
            )
        }
        when (units) {
            0 -> grams()
            1 -> milliliters()
            else -> {
                Log.d("asdasd", "Unit Id outside of range. Couldn't perform calculations")
            }
        }

        return mapOf("Vit1" to vitModel1, "Vit2" to vitModel2, "Vit3" to vitModel3)

    }

    private fun Medana(): Map<String, VitaminEGridModel> {
        fun grams() {
            val density = vitEList[1].density
            val drops = vitEList[1].drops

            val gramsMedana = amount
            var volumeMedana = gramsMedana / density
            volumeMedana = Math.round(volumeMedana * 100.0) / 100.0
            var dropsMedana = volumeMedana * drops
            dropsMedana = dropsMedana.roundToInt().toDouble()
            var howMuchToSell_medana = volumeMedana / 10.0
            howMuchToSell_medana = Math.round(howMuchToSell_medana * 1000.0) / 1000.0
            vitModel1 = VitaminEGridModel(
                main_vit = "Medana",
                main_vit2 = density.toString(),
                mass = amount.toString(),
                volume = volumeMedana.toString(),
                drops = dropsMedana.toString(),
                howMuchTosell = howMuchToSell_medana.toString()
            )

            val gramsHasco = amount
            var volumeHasco = gramsHasco / vitEList[0].density

            volumeHasco = Math.round(volumeHasco * 100.0) / 100.0
            var dropsHasco = volumeHasco * vitEList[0].drops
            dropsHasco = dropsHasco.roundToInt().toDouble()
            var howMuchToSell_Hasco = volumeHasco / 10.0
            howMuchToSell_Hasco = Math.round(howMuchToSell_Hasco * 1000.0) / 1000.0
            vitModel2 = VitaminEGridModel(
                main_vit = "Hasco",
                main_vit2 = vitEList[1].density.toString(),
                mass = amount.toString(),
                volume = volumeHasco.toString(),
                drops = dropsHasco.toString(),
                howMuchTosell = howMuchToSell_Hasco.toString()
            )

            var gramsFagron = volumeMedana * 0.3
            gramsFagron = Math.round(gramsFagron * 100.0) / 100.0
            var volumeFagron = gramsFagron / vitEList[2].density
            volumeFagron = Math.round(volumeFagron * 100.0) / 100.0
            var dropsFagron = gramsFagron / vitEList[2].drops
            dropsFagron = dropsFagron.roundToInt().toDouble()
            var howMuchToSell_Fagron = volumeFagron / 10.0
            howMuchToSell_Fagron = Math.round(howMuchToSell_Fagron * 1000.0) / 1000.0
            vitModel3 = VitaminEGridModel(
                main_vit = "Fagron",
                main_vit2 = vitEList[2].density.toString(),
                mass = gramsFagron.toString(),
                volume = volumeFagron.toString(),
                drops = dropsFagron.toString(),
                howMuchTosell = howMuchToSell_Fagron.toString()
            )
        }

        fun milliliters() {
            val density = vitEList[1].density
            val drops = vitEList[1].drops
            val volumeMedana = amount
            var gramsMedana = volumeMedana * density
            gramsMedana = Math.round(gramsMedana * 100.0) / 100.0
            var dropsMedana = volumeMedana * drops
            dropsMedana = dropsMedana.roundToInt().toDouble()
            var howMuchToSell_medana = volumeMedana / 10.0
            howMuchToSell_medana = Math.round(howMuchToSell_medana * 1000.0) / 1000.0
            vitModel1 = VitaminEGridModel(
                main_vit = "Medana",
                main_vit2 = density.toString(),
                mass = gramsMedana.toString(),
                volume = volumeMedana.toString(),
                drops = dropsMedana.toString(),
                howMuchTosell = howMuchToSell_medana.toString()
            )

            val gramsHasco = amount
            var volumeHasco = gramsHasco / vitEList[0].density

            volumeHasco = Math.round(volumeHasco * 100.0) / 100.0
            var dropsHasco = volumeHasco * vitEList[0].drops
            dropsHasco = dropsHasco.roundToInt().toDouble()
            var howMuchToSell_Hasco = volumeHasco / 10.0
            howMuchToSell_Hasco = Math.round(howMuchToSell_Hasco * 1000.0) / 1000.0
            vitModel2 = VitaminEGridModel(
                main_vit = "Hasco",
                main_vit2 = vitEList[1].density.toString(),
                mass = amount.toString(),
                volume = volumeHasco.toString(),
                drops = dropsHasco.toString(),
                howMuchTosell = howMuchToSell_Hasco.toString()
            )

            var gramsFagron = volumeMedana * 0.3
            gramsFagron = Math.round(gramsFagron * 100.0) / 100.0
            var volumeFagron = gramsFagron / vitEList[2].density
            volumeFagron = Math.round(volumeFagron * 100.0) / 100.0
            var dropsFagron = gramsFagron / vitEList[2].drops
            dropsFagron = dropsFagron.roundToInt().toDouble()
            var howMuchToSell_Fagron = volumeFagron / 10.0
            howMuchToSell_Fagron = Math.round(howMuchToSell_Fagron * 1000.0) / 1000.0
            vitModel3 = VitaminEGridModel(
                main_vit = "Fagron",
                main_vit2 = vitEList[2].density.toString(),
                mass = gramsFagron.toString(),
                volume = volumeFagron.toString(),
                drops = dropsFagron.toString(),
                howMuchTosell = howMuchToSell_Fagron.toString()
            )

        }
        when (units) {
            0 -> grams()
            1 -> milliliters()
            else -> {
                Log.d("asdasd", "Unit Id outside of range. Couldn't perform calculations")
            }
        }

        return mapOf("Vit1" to vitModel1, "Vit2" to vitModel2, "Vit3" to vitModel3)
    }

    private fun Fagron(): Map<String, VitaminEGridModel> {

        val density = vitEList.get(2).density
        val drops = vitEList.get(2).drops
        fun grams() {

            var volumeFagron = amount / density
            volumeFagron = Math.round(volumeFagron * 100.0) / 100.0
            val dropsFagron = (amount / drops).roundToInt()
            var hmts1: Double = volumeFagron / 10
            hmts1 = Math.round(hmts1 * 1000.0) / 1000.0
            vitModel1 = VitaminEGridModel(
                main_vit = "Fagron",
                main_vit2 = density.toString(),
                mass = amount.toString(),
                volume = volumeFagron.toString(),
                drops = dropsFagron.toString(),
                howMuchTosell = hmts1.toString()
            )

            var volumeHasco = amount / 0.3
            volumeHasco = Math.round(volumeHasco * 100.0) / 100.0
            var gramsHasco = volumeHasco * vitEList[0].density
            gramsHasco = Math.round(gramsHasco * 100.0) / 100.0
            val dropsHasco = (volumeHasco * vitEList[0].drops).roundToInt()
            var hmts2: Double = volumeHasco / 10
            hmts2 = Math.round(hmts2 * 1000.0) / 1000.0
            vitModel2 = VitaminEGridModel(
                main_vit = "Hasco",
                main_vit2 = vitEList[0].density.toString(),
                mass = gramsHasco.toString(),
                volume = volumeHasco.toString(),
                drops = dropsHasco.toString(),
                howMuchTosell = hmts2.toString()
            )

            var volumeMedana = amount / 0.3
            volumeMedana = Math.round(volumeMedana * 100.0) / 100.0
            var gramsMedana = volumeMedana * vitEList[0].density
            gramsMedana = Math.round(gramsMedana * 100.0) / 100.0
            val dropsMedana = (volumeMedana * vitEList[0].drops).roundToInt()
            var howMuchToSell_medana: Double = volumeMedana / 10
            howMuchToSell_medana = Math.round(howMuchToSell_medana * 1000.0) / 1000.0
            vitModel3 = VitaminEGridModel(
                main_vit = "Medana",
                main_vit2 = vitEList[0].density.toString(),
                mass = gramsMedana.toString(),
                volume = volumeMedana.toString(),
                drops = dropsMedana.toString(),
                howMuchTosell = howMuchToSell_medana.toString()
            )

        }

        when (units) {
            0 -> grams()
            else -> {
                Log.d("asdasd", "Unit Id outside of range. Couldn't perform calculations")
            }
        }

        return mapOf("Vit1" to vitModel1, "Vit2" to vitModel2)
    }


    fun calculate(): Map<String, VitaminEGridModel> {
        //Selecting company (user input)
        when (company) {
            0 -> return Hasco()
            1 -> return Medana()
            2 -> return Fagron()
            else -> {
                Log.d("VItECalc", "Company Id outside of range. Couldn't perform calculations")
            }
        }
        //Return error Map of VitAGridModels to be used in the grid
        return mapOf(
            "Vit1" to VitaminEGridModel(
                main_vit = "Error",
                main_vit2 = "occurred",
                mass = "Null",
                volume = "Null",
                drops = "Null",
                howMuchTosell = "Null"
            )
        )
    }

}