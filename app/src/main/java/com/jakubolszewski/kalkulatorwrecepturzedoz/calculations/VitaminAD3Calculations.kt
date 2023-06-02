package com.jakubolszewski.kalkulatorwrecepturzedoz.calculations

import android.util.Log
import android.widget.Toast
import com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters.VitaminAD3GridModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitAD3Model
import kotlin.math.roundToInt

class VitaminAD3Calculations(
    val units: Int,
    var amount: Double,
    val vitAD3List: ArrayList<VitAD3Model>
) {
    lateinit var vitModel: VitaminAD3GridModel

    private fun grams(): Map<String, VitaminAD3GridModel> {

        val grams = amount
        var volume = grams / vitAD3List[0].density
        var drops = volume * vitAD3List[0].drops
        volume = Math.round(volume * 100.0) / 100.0
        drops = drops.roundToInt().toDouble()
        var howMuchTosell = grams / 100.0
        howMuchTosell = Math.round(howMuchTosell * 1000.0) / 1000.0
        vitModel = VitaminAD3GridModel(
            main_vit = "Vit. A+d3",
            main_vit2 = "1.09 g/ml",
            mass = grams.toString(),
            volume = volume.toString(),
            drops = drops.toString(),
            howMuchTosell = howMuchTosell.toString()
        )
        return mapOf("Vit" to vitModel)
    }

    private fun milliliters(): Map<String, VitaminAD3GridModel> {
        Log.d("TUTAJ", vitAD3List[0].density.toString())
        Log.d("TUTAJ", vitAD3List[0].drops.toString())
        val volume = amount
        var grams = volume * vitAD3List[0].density
        var drops = volume * vitAD3List[0].drops
        grams = Math.round(grams * 100.0) / 100.0
        drops = drops.roundToInt().toDouble()
        var howMuchTosell = grams / 100.0
        howMuchTosell = Math.round(howMuchTosell * 1000.0) / 1000.0
        vitModel = VitaminAD3GridModel(
            main_vit = "Vit. A+d3",
            main_vit2 = "1.09 g/ml",
            mass = grams.toString(),
            volume = volume.toString(),
            drops = drops.toString(),
            howMuchTosell = howMuchTosell.toString()
        )

        return mapOf("Vit" to vitModel)

    }

    private fun drops(): Map<String, VitaminAD3GridModel> {

        var volume = amount / vitAD3List[0].drops
        volume = Math.round(volume * 100.0) / 100.0
        var grams = volume * vitAD3List[0].density
        grams = Math.round(grams * 100.0) / 100.0

        var howMuchTosell = grams / 100.0
        howMuchTosell = Math.round(howMuchTosell * 1000.0) / 1000.0
        vitModel = VitaminAD3GridModel(
            main_vit = "Vit. A+d3",
            main_vit2 = "1.09 g/ml",
            mass = grams.toString(),
            volume = volume.toString(),
            drops = amount.toString(),
            howMuchTosell = howMuchTosell.toString()
        )


        return mapOf("Vit" to vitModel)
    }


    fun calculate(): Map<String, VitaminAD3GridModel> {
        //Selecting company (user input)
        when (units) {
            0 -> return grams()
            1 -> return milliliters()
            2 -> return drops()
            else -> {
                Log.d("adas", "Unit Id outside of range. Couldn't perform calculations")
            }
        }
        //Return error Map of VitAGridModels to be used in the grid
        return mapOf(
            "Vit" to VitaminAD3GridModel(
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