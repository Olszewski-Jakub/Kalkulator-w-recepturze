package com.jakubolszewski.kalkulatorwrecepturzedoz.calculations

import android.util.Log
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.OlejkiGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.OlejkiModel
import kotlin.math.roundToInt

private var TAG = "OlejkiCalculations"

class OlejkiCalculations(
    val type: Int,
    val units: Int,
    var amount: Double,
    val olejkiList: ArrayList<OlejkiModel>
) {
    lateinit var olejkiModel: OlejkiGridModel


    private fun Mint(): Map<String, OlejkiGridModel> {

        fun grams() {
            val grams = amount
            var volume = grams / olejkiList[0].density
            volume = Math.round(volume * 100.0) / 100.0
            var drops = grams / olejkiList[0].drops
            drops = drops.roundToInt().toDouble()
            olejkiModel = OlejkiGridModel(
                "Olejek miętowy",
                olejkiList[0].density.toString(),
                grams.toString(),
                volume.toString(),
                drops.toString()
            )
        }

        fun mililiters() {
            val mililiters = amount
            var grams = mililiters * olejkiList[0].density
            grams = Math.round(grams * 100.0) / 100.0
            var drops = mililiters / olejkiList[0].drops
            drops = drops.roundToInt().toDouble()
            olejkiModel = OlejkiGridModel(
                "Olejek miętowy",
                olejkiList[0].density.toString(),
                grams.toString(),
                mililiters.toString(),
                drops.toString()
            )
        }

        fun drops() {
            val drops = amount
            var grams = drops * olejkiList[0].drops
            grams = Math.round(grams * 100.0) / 100.0
            var volume = grams / olejkiList[0].density
            volume = Math.round(volume * 100.0) / 100.0
            olejkiModel = OlejkiGridModel(
                "Olejek miętowy",
                olejkiList[0].density.toString(),
                grams.toString(),
                volume.toString(),
                drops.toString()
            )
        }

        when (units) {
            0 -> grams()
            1 -> mililiters()
            2 -> drops()
            else -> {
                Log.d(TAG, "Units Id outside of range. Couldn't perform calculations")
            }
        }


        return mapOf("olejek" to olejkiModel)
    }

    private fun Lavender(): Map<String, OlejkiGridModel> {

        fun grams() {
            val grams = amount
            var volume = grams / olejkiList[1].density
            volume = Math.round(volume * 100.0) / 100.0
            var drops = grams / olejkiList[1].drops
            drops = drops.roundToInt().toDouble()
            olejkiModel = OlejkiGridModel(
                "Olejek lawendowy",
                olejkiList[1].density.toString(),
                grams.toString(),
                volume.toString(),
                drops.toString()
            )
        }

        fun mililiters() {
            val mililiters = amount
            var grams = mililiters * olejkiList[1].density
            grams = Math.round(grams * 100.0) / 100.0
            var drops = mililiters / olejkiList[1].drops
            drops = drops.roundToInt().toDouble()
            olejkiModel = OlejkiGridModel(
                "Olejek lawendowy",
                olejkiList[1].density.toString(),
                grams.toString(),
                mililiters.toString(),
                drops.toString()
            )
        }

        fun drops() {
            val drops = amount
            var grams = drops * olejkiList[1].drops
            grams = Math.round(grams * 100.0) / 100.0
            var volume = grams / olejkiList[1].density
            volume = Math.round(volume * 100.0) / 100.0
            olejkiModel = OlejkiGridModel(
                "Olejek lawendowy",
                olejkiList[1].density.toString(),
                grams.toString(),
                volume.toString(),
                drops.toString()
            )
        }

        when (units) {
            0 -> grams()
            1 -> mililiters()
            2 -> drops()
            else -> {
                Log.d(TAG, "Units Id outside of range. Couldn't perform calculations")
            }
        }


        return mapOf("olejek" to olejkiModel)
    }

    private fun Eucalyptus(): Map<String, OlejkiGridModel> {
        fun grams() {
            val grams = amount
            var volume = grams / olejkiList[2].density
            volume = Math.round(volume * 100.0) / 100.0
            var drops = grams / olejkiList[2].drops
            drops = drops.roundToInt().toDouble()
            olejkiModel = OlejkiGridModel(
                "Olejek eukaliptusowy",
                olejkiList[2].density.toString(),
                grams.toString(),
                volume.toString(),
                drops.toString()
            )
        }

        fun mililiters() {
            val mililiters = amount
            var grams = mililiters * olejkiList[2].density
            grams = Math.round(grams * 100.0) / 100.0
            var drops = mililiters / olejkiList[2].drops
            drops = drops.roundToInt().toDouble()
            olejkiModel = OlejkiGridModel(
                "Olejek eukaliptusowy",
                olejkiList[2].density.toString(),
                grams.toString(),
                mililiters.toString(),
                drops.toString()
            )
        }

        fun drops() {
            val drops = amount
            var grams = drops * olejkiList[2].drops
            grams = Math.round(grams * 100.0) / 100.0
            var volume = grams / olejkiList[2].density
            volume = Math.round(volume * 100.0) / 100.0
            olejkiModel = OlejkiGridModel(
                "Olejek eukaliptusowy",
                olejkiList[2].density.toString(),
                grams.toString(),
                volume.toString(),
                drops.toString()
            )
        }

        when (units) {
            0 -> grams()
            1 -> mililiters()
            2 -> drops()
            else -> {
                Log.d(TAG, "Units Id outside of range. Couldn't perform calculations")
            }
        }


        return mapOf("olejek" to olejkiModel)
    }

    fun calculate(): Map<String, OlejkiGridModel> {
        //Selecting company (user input)
        when (type) {
            0 -> return Mint()
            1 -> return Lavender()
            2 -> return Eucalyptus()
            else -> {
                Log.d(TAG, "Type Id outside of range. Couldn't perform calculations")
            }
        }
        //Return error Map of VitAGridModels to be used in the grid
        return mapOf(
            "Vit1" to OlejkiGridModel(
                main_olejek = "Error",
                main_olejke2 = "occurred",
                mass = "Null",
                volume = "Null",
                drops = "Null",
            )
        )
    }

}