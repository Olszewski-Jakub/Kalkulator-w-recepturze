package com.jakubolszewski.kalkulatorwrecepturzedoz.database

import android.content.Context
import android.util.Log
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.AlcoholConcentrationModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.AlcoholDegreeModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.OlejeModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.OlejkiModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitAD3Model
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitAModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitEModel
import org.json.JSONArray
import org.json.JSONObject


class ImportData(mContext: Context) {

    private var dbHelper = DBHelper(context = mContext)
    fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
        when (val value = this[it]) {
            is JSONArray -> {
                val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
                JSONObject(map).toMap().values.toList()
            }

            is JSONObject -> value.toMap()
            JSONObject.NULL -> null
            else -> value
        }
    }

    //TODO: Database is unable to update records
    // "android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tbl_vit_a.id (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
    fun updateDataBase(jsonString: String) {
        //Convert JSON String to Map
        Log.d("ImportData", jsonString)
        val jsonObj = JSONObject(jsonString)
        val jsonMap = jsonObj.toMap()

        //Save data according vitamin A from Firebase Remote Config to SQLite database
        val vitaminAData: String = jsonMap.getValue("vit_A").toString()
        val vitaminAJsonObject = JSONObject(vitaminAData)
        val vitaminAMap = vitaminAJsonObject.toMap()

        //Crearte Map if different values of VitA
        convertVItA(
            JSONObject(vitaminAMap["hasco"].toString()).toMap(),
            JSONObject(vitaminAMap["medana"].toString()).toMap(),
            JSONObject(vitaminAMap["fagron"].toString()).toMap()
        )
        val vitaminEData: String = jsonMap.getValue("vit_E").toString()
        val vitaminEJsonObject = JSONObject(vitaminEData)
        val vitaminEMap = vitaminEJsonObject.toMap()
        convertVitE(
            JSONObject(vitaminEMap["hasco"].toString()).toMap(),
            JSONObject(vitaminEMap["medana"].toString()).toMap(),
            JSONObject(vitaminEMap["fagron"].toString()).toMap()
        )

        //Save data according Alcohol from Firebase Remote Config to SQLite database
        val alcoholData_concentration = jsonMap.getValue("alkohol_concentration").toString()
        val alcoholJsonObject_concentration = JSONObject(alcoholData_concentration)
        val alcoholMap_concentration = alcoholJsonObject_concentration.toMap()

        val alcoholData_degrees = jsonMap.getValue("alkohol_degrees").toString().replace("=", "-=")
        val alcoholJsonObject_degrees = JSONObject(alcoholData_degrees)
        val alcoholMap_degrees = alcoholJsonObject_degrees.toMap()

        convertAlcoholConcentration(alcoholMap_concentration)
        convertAlcholDegrees(alcoholMap_degrees)

        //Save data according Oleje from Firebase Remote Config to SQLite database
        val vitaminAD3Data = jsonMap.getValue("vit_A_D3").toString().toString()
        val vitaminAD3JsonObject = JSONObject(vitaminAD3Data)
        val vitaminAD3Map = vitaminAD3JsonObject.toMap()
        convertVitAD3(vitaminAD3Map)

        //Save data according Oleje from Firebase Remote Config to SQLite database
        val olejeData = jsonMap.getValue("oleje").toString().toString()
        val olejeJsonObject = JSONObject(olejeData)
        val olejeMap = olejeJsonObject.toMap()
        convertOleje(olejeMap)

        //Save data according Olejki from Firebase Remote Config to SQLite database
        val olejkiData = jsonMap.getValue("olejki").toString().toString()
        val olejkiJsonObject = JSONObject(olejkiData)
        val olejkiMap = olejkiJsonObject.toMap()

        convertOlejki(
            JSONObject(olejkiMap["mint"].toString()).toMap(),
            JSONObject(olejkiMap["lavender"].toString()).toMap(),
            JSONObject(olejkiMap["eucalyptus"].toString()).toMap()
        )

    }

    private fun convertVItA(
        hascoMap: Map<String, *>,
        medanaMap: Map<String, *>,
        fagronMap: Map<String, *>
    ) {
        //Create Map of VitA models to save in database
        val hascoModel = VitAModel(
            id = 0,
            company = "hasco",
            density = hascoMap["density"].toString().toDouble(),
            drops = hascoMap["drops"].toString().toInt(),
            mass_units = hascoMap["mass_units"].toString().toDouble()
        )
        val medanaModel = VitAModel(
            id = 1,
            company = "medana",
            density = medanaMap["density"].toString().toDouble(),
            drops = medanaMap["drops"].toString().toInt(),
            mass_units = medanaMap["mass_units"].toString().toDouble()
        )
        val fagronModel = VitAModel(
            id = 2,
            company = "fagron",
            density = fagronMap["density"].toString().toDouble(),
            drops = fagronMap["drops"].toString().toInt(),
            mass_units = fagronMap.get("mass_units").toString().toDouble()
        )
        //TODO Needs to be fixed. Implement isVitAEmpty() method it probably needs fixing.
        // Priority is not high app doesn't crash, throws an error in logcat
        //if (true){
        val hasco_status = dbHelper.insertVitA(hascoModel)
        val medana_status = dbHelper.insertVitA(medanaModel)
        val fagron_status = dbHelper.insertVitA(fagronModel)

        if (hasco_status > -1 && medana_status > -1 && fagron_status > -1) {
        }
        //}else {
        //Update data in database
        dbHelper.updateVitA(hascoModel)
        dbHelper.updateVitA(medanaModel)
        dbHelper.updateVitA(fagronModel)
        //}

    }

    private fun convertVitE(
        hascoMap: Map<String, *>,
        medanaMap: Map<String, *>,
        fagronMap: Map<String, *>
    ) {
        val hascoModel = VitEModel(
            id = 0,
            company = "hasco",
            density = hascoMap["density"].toString().toDouble(),
            drops = hascoMap["drops"].toString().toDouble(),
        )
        val medanaModel = VitEModel(
            id = 1,
            company = "hasco",
            density = medanaMap["density"].toString().toDouble(),
            drops = medanaMap["drops"].toString().toDouble(),
        )
        val fagronModel = VitEModel(
            id = 2,
            company = "fagron",
            density = fagronMap["density"].toString().toDouble(),
            drops = fagronMap["drops"].toString().toDouble(),
        )
        dbHelper.insertVitE(hascoModel)
        dbHelper.insertVitE(medanaModel)
        dbHelper.insertVitE(fagronModel)
    }

    private fun convertAlcoholConcentration(
        alcoholMap: Map<String, *>
    ) {
        var check = dbHelper.isAlcoholConcentrationEmpty()
        if (!check) {
            var id: Int = 0
            for (x in alcoholMap) {
                val alcoholModel = AlcoholConcentrationModel(
                    id = id,
                    alcohol_concentration = x.key,
                    alcohol_volume = x.value.toString().toDouble()
                )
                id++
                dbHelper.insertAlcoholConcentration(alcoholModel)
            }
        }

    }

    private fun convertAlcholDegrees(alcoholMap: Map<String, *>) {
        var check = dbHelper.isAlcoholDegreeEmpty()
        if (!check) {
            var id: Int = 0
            for (x in alcoholMap) {
                val alcoholModel = AlcoholDegreeModel(
                    id = id,
                    alcohol_degree = x.key.replace("-", ""),
                    alcohol_volume_degree = x.value.toString().toDouble()
                )
                dbHelper.insertAlcoholDegree(alcoholModel)
                id++
            }

        }
    }

    private fun convertVitAD3(
        vitAD3Map: Map<String, *>
    ) {
        val vitAD3Model = VitAD3Model(
            id = 0,
            density = vitAD3Map["density"].toString().toDouble(),
            drops = vitAD3Map["drops"].toString().toInt(),
        )
        dbHelper.insertVitAD3(vitAD3Model)
    }

    private fun convertOleje(olejeMap: Map<String, *>) {

        val olejeModel_rapae = OlejeModel(
            id = 0,
            type = "rapae",
            density = olejeMap["oleum_rapae"].toString().toDouble(),
        )

        val olejeModel_ricini = OlejeModel(
            id = 1,
            type = "ricini",
            density = olejeMap["oleum_ricini"].toString().toDouble(),
        )

        dbHelper.insertOleje(olejeModel_rapae)
        dbHelper.insertOleje(olejeModel_ricini)

    }

    private fun convertOlejki(
        oljkiMap_mint: Map<String, *>,
        oljkiMap_lavender: Map<String, *>,
        oljkiMap_eucalyptus: Map<String, *>
    ) {
        val olejkiModel_mint = OlejkiModel(
            id = 0,
            type = "mint",
            density = oljkiMap_mint["density"].toString().toDouble(),
            drops = oljkiMap_mint["drops"].toString().toDouble(),
        )

        val olejkiModel_lavender = OlejkiModel(
            id = 1,
            type = "lavender",
            density = oljkiMap_lavender["density"].toString().toDouble(),
            drops = oljkiMap_lavender["drops"].toString().toDouble(),
        )

        val olejkiModel_eucalyptus = OlejkiModel(
            id = 2,
            type = "eucalyptus",
            density = oljkiMap_eucalyptus["density"].toString().toDouble(),
            drops = oljkiMap_eucalyptus["drops"].toString().toDouble(),
        )
        dbHelper.insertOlejki(olejkiModel_mint)
        dbHelper.insertOlejki(olejkiModel_lavender)
        dbHelper.insertOlejki(olejkiModel_eucalyptus)
    }
}
