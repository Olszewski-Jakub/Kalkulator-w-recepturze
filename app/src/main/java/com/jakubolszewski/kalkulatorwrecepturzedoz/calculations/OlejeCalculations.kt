package com.jakubolszewski.kalkulatorwrecepturzedoz.calculations

import android.util.Log
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.OlejGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.OlejeModel

class OlejeCalculations(
    val type: Int,
    val units: Int,
    val amount: Double,
    val densityGiven: Double = -1.0,
    val olejeList: ArrayList<OlejeModel>
) {
    lateinit var olej: OlejGridModel
    var TAG = "OlejeCalculations"

    private fun Rapae(): Map<String, OlejGridModel> {

        var density = if (densityGiven == -1.0) {
            olejeList[0].density
        } else {
            densityGiven
        }

        fun grams() {
            val grams = amount
            var volume = grams / density
            volume = Math.round(volume * 100.0) / 100.0
            olej = OlejGridModel(
                main_olej = "Oleum Rapae",
                main_olej2 = density.toString(),
                mass = grams.toString(),
                volume = volume.toString()
            )
        }

        fun mililiters() {
            val volume = amount
            var grams = volume * density
            grams = Math.round(grams * 100.0) / 100.0
            olej = OlejGridModel(
                main_olej = "Oleum Rapae",
                main_olej2 = density.toString(),
                mass = grams.toString(),
                volume = volume.toString()
            )
        }

        when (units) {
            0 -> grams()
            1 -> mililiters()
            else -> {
                Log.d(TAG, "Units Id outside of range. Couldn't perform calculations")
            }
        }

        return mapOf("olej" to olej)
    }

    private fun Ricini(): Map<String, OlejGridModel> {

        val density = if (densityGiven == -1.0) {
            olejeList[1].density
        } else {
            densityGiven
        }

        fun grams() {
            val grams = amount
            var volume = grams / density
            volume = Math.round(volume * 100.0) / 100.0
            olej = OlejGridModel(
                main_olej = "Oleum Rapae",
                main_olej2 = density.toString(),
                mass = grams.toString(),
                volume = volume.toString()
            )
        }

        fun mililiters() {
            val volume = amount
            var grams = volume * density
            grams = Math.round(grams * 100.0) / 100.0
            olej = OlejGridModel(
                main_olej = "Oleum Rapae",
                main_olej2 = density.toString(),
                mass = grams.toString(),
                volume = volume.toString()
            )
        }

        when (units) {
            0 -> grams()
            1 -> mililiters()
            else -> {
                Log.d(TAG, "Units Id outside of range. Couldn't perform calculations")
            }
        }

        return mapOf("olej" to olej)
    }

    fun calculate(): Map<String, OlejGridModel> {
        //Selecting company (user input)
        Log.d(TAG, "Type: $type $units ")
        when (type) {
            0 -> return Rapae()
            1 -> return Ricini()
            else -> {
                Log.d(TAG, "Type Id outside of range. Couldn't perform calculations")
            }
        }
        //Return error Map of VitAGridModels to be used in the grid
        return mapOf(
            "olej" to OlejGridModel(
                main_olej = "Error",
                main_olej2 = "occurred",
                mass = "Null",
                volume = "Null",

                )
        )
    }
}