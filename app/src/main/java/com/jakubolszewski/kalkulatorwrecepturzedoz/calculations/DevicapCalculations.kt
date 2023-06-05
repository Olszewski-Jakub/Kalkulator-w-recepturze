package com.jakubolszewski.kalkulatorwrecepturzedoz.calculations

import android.util.Log
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.DevicapGridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.DevicapModel

private const val TAG = "DevicapCalculations"

class DevicapCalculations(
    val units: Int,
    var amount: Double,
    val devicapList: ArrayList<DevicapModel>
) {
    lateinit var vitModel: DevicapGridModel

    private fun grams(): Map<String, DevicapGridModel> {
        val grams = amount
        var volume = grams / devicapList[0].density
        var drops = volume * devicapList[0].drops
        var massunits = volume * devicapList[0].massUnits
        var howMuchTosell = volume / 10

        volume = Math.round(volume * 100.0) / 100.0
        drops = Math.round(drops * 100.0) / 100.0
        massunits = Math.round(massunits * 100.0) / 100.0
        howMuchTosell = Math.round(howMuchTosell * 1000.0) / 1000.0

        vitModel = DevicapGridModel(
            main_vit = "Devicap",
            main_vit2 = devicapList[0].density.toString(),
            mass = grams.toString(),
            volume = volume.toString(),
            drops = drops.toString(),
            massunits = massunits.toString(),
            howMuchTosell = howMuchTosell.toString()
        )
        return mapOf("Vit1" to vitModel)
    }

    private fun mililiters(): Map<String, DevicapGridModel> {

        val volume = amount
        var grams = volume * devicapList[0].density
        var drops = volume * devicapList[0].drops
        var massunits = volume * devicapList[0].massUnits
        var howMuchTosell = volume / 10
        grams = Math.round(grams * 100.0) / 100.0
        drops = Math.round(drops * 100.0) / 100.0
        massunits = Math.round(massunits * 100.0) / 100.0
        howMuchTosell = Math.round(howMuchTosell * 1000.0) / 1000.0

        vitModel = DevicapGridModel(
            main_vit = "Devicap",
            main_vit2 = devicapList[0].density.toString(),
            mass = grams.toString(),
            volume = volume.toString(),
            drops = drops.toString(),
            massunits = massunits.toString(),
            howMuchTosell = howMuchTosell.toString()
        )


        return mapOf("Vit1" to vitModel)
    }

    private fun massUnits(): Map<String, DevicapGridModel> {

        val massunits = amount
        var volume = massunits / devicapList[0].massUnits
        var grams = volume * devicapList[0].density
        var drops = volume * devicapList[0].drops
        var howMuchTosell = volume / 10

        volume = Math.round(volume * 100.0) / 100.0
        grams = Math.round(grams * 100.0) / 100.0
        drops = Math.round(drops * 100.0) / 100.0
        howMuchTosell = Math.round(howMuchTosell * 1000.0) / 1000.0

        vitModel = DevicapGridModel(
            main_vit = "Devicap",
            main_vit2 = devicapList[0].density.toString(),
            mass = grams.toString(),
            volume = volume.toString(),
            drops = drops.toString(),
            massunits = massunits.toString(),
            howMuchTosell = howMuchTosell.toString()
        )

        return mapOf("Vit1" to vitModel)
    }

    private fun drops(): Map<String, DevicapGridModel> {
        val drops = amount
        var volume = drops / devicapList[0].drops
        var grams = volume * devicapList[0].density
        var massunits = volume * devicapList[0].massUnits
        var howMuchTosell = volume / 10

        volume = Math.round(volume * 100.0) / 100.0
        grams = Math.round(grams * 100.0) / 100.0
        massunits = Math.round(massunits * 100.0) / 100.0
        howMuchTosell = Math.round(howMuchTosell * 1000.0) / 1000.0

        vitModel = DevicapGridModel(
            main_vit = "Devicap",
            main_vit2 = devicapList[0].density.toString(),
            mass = grams.toString(),
            volume = volume.toString(),
            drops = drops.toString(),
            massunits = massunits.toString(),
            howMuchTosell = howMuchTosell.toString()
        )

        return mapOf("Vit1" to vitModel)
    }


    fun calculate(): Map<String, DevicapGridModel> {
        Log.d(TAG, "calculate: $units , $amount")
        when (units) {
            0 -> return grams()
            1 -> return mililiters()
            2 -> return massUnits()
            3 -> return drops()
            else -> {
                Log.d(TAG, "Company Id outside of range. Couldn't perform calculations")
            }
        }

        return mapOf(
            "Vit1" to DevicapGridModel(
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