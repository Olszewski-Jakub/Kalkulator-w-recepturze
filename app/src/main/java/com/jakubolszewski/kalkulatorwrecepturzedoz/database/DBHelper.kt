package com.jakubolszewski.kalkulatorwrecepturzedoz.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.AlcoholConcentrationModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.AlcoholDegreeModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.OlejeModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.OlejkiModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitAD3Model
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitAModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models.VitEModel

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Object with all the columns names
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "KWK.db"

        // Table VitA
        private const val ID = "id"
        private const val TBL_VITA = "tbl_vit_a"
        private const val COMPANY = "company"
        private const val DENSITY = "density"
        private const val DROPS = "drops"
        private const val MASS_UNITS = "mass_units"

        //Table Vit E
        private const val TBL_VITE = "tbl_vit_e"

        //Table Alcohol Concentration
        private const val TBL_ALCOHOL_CONCENTRATION = "tbl_alcohol_concentration"
        private const val ALCOHOL_CONCENTRATION = "alcohol_concentration"
        private const val ALCOHOL_VOLUME = "alcohol_volume"

        //Table Alcohol Degree
        private const val TBL_ALCOHOL_DEGREE = "tbl_alcohol_degree"
        private const val ALCOHOL_DEGREE = "alcohol_degree"
        private const val ALCOHOL_VOLUME_DEGREE = "alcohol_volume_degree"

        // Table VitA+D3
        private const val TBL_VITAD3 = "tbl_vit_a_d3"

        // Table oleje
        private const val TBL_OLEJE = "tbl_oleje"

        // Table olejki
        private const val TBL_OLEJKI = "tbl_olejki"
        private const val TYPE = "type"
    }

    //Create table SQL query
    override fun onCreate(db: SQLiteDatabase?) {
        // Create table VitA
        val createTblVitA =
            ("CREATE TABLE $TBL_VITA ($ID INTEGER PRIMARY KEY,$COMPANY TEXT,$DENSITY NUMERIC,$DROPS INTEGER,$MASS_UNITS NUMERIC )")
        db?.execSQL(createTblVitA)

        //Create table vitE
        val createTblVitE =
            ("CREATE TABLE $TBL_VITE ($ID INTEGER PRIMARY KEY,$COMPANY TEXT,$DENSITY NUMERIC,$DROPS INTEGER)")
        db?.execSQL(createTblVitE)

        // Create table Alcohol Concentration
        val createTblAlcoholConcentration =
            ("CREATE TABLE $TBL_ALCOHOL_CONCENTRATION ($ID INTEGER PRIMARY KEY,$ALCOHOL_CONCENTRATION TEXT,$ALCOHOL_VOLUME NUMERIC )")
        db?.execSQL(createTblAlcoholConcentration)

        // Create table Alcohol Degree
        val createTblAlcoholDegree =
            ("CREATE TABLE $TBL_ALCOHOL_DEGREE ($ID INTEGER PRIMARY KEY,$ALCOHOL_DEGREE TEXT,$ALCOHOL_VOLUME_DEGREE NUMERIC )")
        db?.execSQL(createTblAlcoholDegree)

        // Create table VitA+D3
        val createTblVitAD3 =
            ("CREATE TABLE $TBL_VITAD3 ($ID INTEGER PRIMARY KEY,$DENSITY NUMERIC,$DROPS INTEGER)")
        db?.execSQL(createTblVitAD3)

        // Create table oleje
        val createTblOleje =
            ("CREATE TABLE $TBL_OLEJE ($ID INTEGER PRIMARY KEY,$TYPE TEXT,$DENSITY NUMERIC)")
        db?.execSQL(createTblOleje)

        // CReate table olejki
        val createTblOlejki =
            ("CREATE TABLE $TBL_OLEJKI ($ID INTEGER PRIMARY KEY,$TYPE TEXT,$DENSITY NUMERIC,$DROPS INTEGER)")
        db?.execSQL(createTblOlejki)
    }

    //Upgrade table SQL query
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_VITA")
        db.execSQL("DROP TABLE IF EXISTS $TBL_ALCOHOL_CONCENTRATION")
        db.execSQL("DROP TABLE IF EXISTS $TBL_ALCOHOL_DEGREE")
        db.execSQL("DROP TABLE IF EXISTS $TBL_VITE")
        db.execSQL("DROP TABLE IF EXISTS $TBL_VITAD3")
        db.execSQL("DROP TABLE IF EXISTS $TBL_OLEJE")
        db.execSQL("DROP TABLE IF EXISTS $TBL_OLEJKI")
        onCreate(db)
    }

    // Insert data into table VitA
    fun insertVitA(vitA: VitAModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, vitA.id)
        contentValues.put(COMPANY, vitA.company)
        contentValues.put(DENSITY, vitA.density)
        contentValues.put(DROPS, vitA.drops)
        contentValues.put(MASS_UNITS, vitA.mass_units)

        val success = db.insert(TBL_VITA, null, contentValues)
        db.close()
        return success
    }

    //Check if table VitA is empty
    @SuppressLint("Recycle")
    fun isVitATableEmpty(): Boolean {
        val db = this.readableDatabase
        val mcursor = db.rawQuery("SELECT count(*) FROM $TBL_VITA", null)
        return mcursor.count == 0
    }

    //Update data in table VitA
    fun updateVitA(vitA: VitAModel) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, vitA.id)
        contentValues.put(COMPANY, vitA.company)
        contentValues.put(DENSITY, vitA.density)
        contentValues.put(DROPS, vitA.drops)
        contentValues.put(MASS_UNITS, vitA.mass_units)

        db.update(TBL_VITA, contentValues, "id=?", arrayOf(vitA.id.toString()))
        db.close()

    }

    // Get all data from table VitA and return it as a list of VitAModel
    @SuppressLint("Range")
    fun getAllVitA(): ArrayList<VitAModel> {
        val vitAList: ArrayList<VitAModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_VITA"
        val db = this.readableDatabase

        // Cursor is a pointer to a row in the table
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var company: String
        var density: Double
        var drops: Int
        var mass_units: Double

        // Move cursor to the first row
        if (cursor.moveToFirst()) {
            // Loop through the cursor while it is not at the end of the table
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID))
                company = cursor.getString(cursor.getColumnIndex(COMPANY))
                density = cursor.getDouble(cursor.getColumnIndex(DENSITY))
                drops = cursor.getInt(cursor.getColumnIndex(DROPS))
                mass_units = cursor.getDouble(cursor.getColumnIndex(MASS_UNITS))
                // Create a VitAModel object and add it to the list
                val vita = VitAModel(
                    id = id,
                    company = company,
                    density = density,
                    drops = drops,
                    mass_units = mass_units
                )
                vitAList.add(vita)
            } while (cursor.moveToNext())
        }
        // Close the cursor and the database
        cursor.close()
        db.close()
        return vitAList
    }

    // Insert data into table VitE
    fun insertVitE(vitEModel: VitEModel) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, vitEModel.id)
        contentValues.put(COMPANY, vitEModel.company)
        contentValues.put(DENSITY, vitEModel.density)
        contentValues.put(DROPS, vitEModel.drops)

        val success = db.insert(TBL_VITE, null, contentValues)
        db.close()
    }

    fun updateVitA(vitE: VitEModel) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, vitE.id)
        contentValues.put(COMPANY, vitE.company)
        contentValues.put(DENSITY, vitE.density)
        contentValues.put(DROPS, vitE.drops)

        db.update(TBL_VITE, contentValues, "id=?", arrayOf(vitE.id.toString()))
        db.close()

    }

    // Get all data from table VitA and return it as a list of VitAModel
    @SuppressLint("Range")
    fun getAllVitE(): ArrayList<VitEModel> {
        val vitEList: ArrayList<VitEModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_VITE"
        val db = this.readableDatabase

        // Cursor is a pointer to a row in the table
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var company: String
        var density: Double
        var drops: Double
        var mass_units: Double

        // Move cursor to the first row
        if (cursor.moveToFirst()) {
            // Loop through the cursor while it is not at the end of the table
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID))
                company = cursor.getString(cursor.getColumnIndex(COMPANY))
                density = cursor.getDouble(cursor.getColumnIndex(DENSITY))
                drops = cursor.getDouble(cursor.getColumnIndex(DROPS))
                mass_units = cursor.getDouble(cursor.getColumnIndex(MASS_UNITS))
                // Create a VitAModel object and add it to the list
                val vitE = VitEModel(
                    id = id,
                    company = company,
                    density = density,
                    drops = drops,
                )
                vitEList.add(vitE)
            } while (cursor.moveToNext())
        }
        // Close the cursor and the database
        cursor.close()
        db.close()
        return vitEList
    }


    // Insert data into table Alcohol Concentration
    @SuppressLint("Range")
    fun insertAlcoholConcentration(alcoholConcentration: AlcoholConcentrationModel): Long {
        val db = this.readableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, alcoholConcentration.id)
        contentValues.put(
            ALCOHOL_CONCENTRATION,
            alcoholConcentration.alcohol_concentration.replace("°(", "° (").replace("mm", " m/m")
        )
        contentValues.put(ALCOHOL_VOLUME, alcoholConcentration.alcohol_volume)
        Log.d(
            "AlcoholConcentration",
            alcoholConcentration.alcohol_concentration.replace("°(", "° (").replace("mm", " m/m")
        )
        val success = db.insert(TBL_ALCOHOL_CONCENTRATION, null, contentValues)
        db.close()
        return success
    }

    //Check if data exists in table Alcohol Concentration
    @SuppressLint("Recycle")
    fun isAlcoholConcentrationEmpty(): Boolean {
        val db = this.readableDatabase
        val mCursor: Cursor? = db.rawQuery("SELECT * FROM $TBL_ALCOHOL_CONCENTRATION", null)
        return mCursor!!.count > 0
    }

    //Update data in table Alcohol Concentration
    fun updateAlcoholConcentration(alcoholConcentration: AlcoholConcentrationModel) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, alcoholConcentration.id)
        contentValues.put(ALCOHOL_CONCENTRATION, alcoholConcentration.alcohol_concentration)
        contentValues.put(ALCOHOL_VOLUME, alcoholConcentration.alcohol_volume)

        db.update(
            TBL_ALCOHOL_CONCENTRATION,
            contentValues,
            "id=?",
            arrayOf(alcoholConcentration.id.toString())
        )
        db.close()

    }

    // Get all data from table Alcohol Concentration and return it as a list of AlcoholConcentrationModel
    @SuppressLint("Range")
    fun getAllAlcoholConcentration(): ArrayList<AlcoholConcentrationModel> {
        val alcoholConcentrationList: ArrayList<AlcoholConcentrationModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_ALCOHOL_CONCENTRATION"
        val db = this.readableDatabase

        // Cursor is a pointer to a row in the table
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var alcohol_concentration: String
        var alcohol_volume: Double

        // Move cursor to the first row
        if (cursor.moveToFirst()) {
            // Loop through the cursor while it is not at the end of the table
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID))
                alcohol_concentration =
                    cursor.getString(cursor.getColumnIndex(ALCOHOL_CONCENTRATION))
                alcohol_volume = cursor.getDouble(cursor.getColumnIndex(ALCOHOL_VOLUME))
                // Create a AlcoholConcentrationModel object and add it to the list
                val alcoholConcentration = AlcoholConcentrationModel(
                    id = id,
                    alcohol_concentration = alcohol_concentration,
                    alcohol_volume = alcohol_volume
                )
                alcoholConcentrationList.add(alcoholConcentration)
            } while (cursor.moveToNext())
        }
        // Close the cursor and the database
        cursor.close()
        db.close()
        return alcoholConcentrationList
    }

    // Insert data into table Alcohol Degree
    fun insertAlcoholDegree(alcoholDegree: AlcoholDegreeModel) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, alcoholDegree.id)
        contentValues.put(ALCOHOL_DEGREE, alcoholDegree.alcohol_degree.toString())
        contentValues.put(ALCOHOL_VOLUME_DEGREE, alcoholDegree.alcohol_volume_degree.toDouble())

        db.insert(TBL_ALCOHOL_DEGREE, null, contentValues)
        db.close()
//        return success
    }

    fun isAlcoholDegreeEmpty(): Boolean {
        val db = this.readableDatabase
        val mCursor: Cursor? = db.rawQuery("SELECT * FROM $TBL_ALCOHOL_DEGREE", null)
        return mCursor!!.count > 0
    }

    //Update data in table Alcohol Degree
    fun updateAlcoholDegree(alcoholDegree: AlcoholDegreeModel) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, alcoholDegree.id)
        contentValues.put(ALCOHOL_DEGREE, alcoholDegree.alcohol_degree)
        contentValues.put(ALCOHOL_VOLUME, alcoholDegree.alcohol_volume_degree)
        db.update(TBL_ALCOHOL_DEGREE, contentValues, "id=?", arrayOf(alcoholDegree.id.toString()))
        db.close()

    }

    // Get all data from table Alcohol Degree and return it as a list of AlcoholDegreeModel
    @SuppressLint("Range")
    fun getAllAlcoholDegree(): ArrayList<AlcoholDegreeModel> {
        val alcoholDegreeList: ArrayList<AlcoholDegreeModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_ALCOHOL_DEGREE"
        val db = this.readableDatabase

        // Cursor is a pointer to a row in the table
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var alcohol_degree: String
        var alcohol_volume_degree: Double

        // Move cursor to the first row
        if (cursor.moveToFirst()) {
            // Loop through the cursor while it is not at the end of the table
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID))
                alcohol_degree = cursor.getString(cursor.getColumnIndex(ALCOHOL_DEGREE))
                alcohol_volume_degree = cursor.getDouble(cursor.getColumnIndex(ALCOHOL_VOLUME))
                // Create a AlcoholDegreeModel object and add it to the list
                val alcoholDegree = AlcoholDegreeModel(
                    id = id,
                    alcohol_degree = alcohol_degree,
                    alcohol_volume_degree = alcohol_volume_degree
                )
                alcoholDegreeList.add(alcoholDegree)
            } while (cursor.moveToNext())
        }
        // Close the cursor and the database
        cursor.close()
        db.close()
        return alcoholDegreeList
    }


    // Insert data into table Vitamin A+D3
    fun insertVitAD3(vitaminAD3: VitAD3Model): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, vitaminAD3.id)
        contentValues.put(DENSITY, vitaminAD3.density)
        contentValues.put(DROPS, vitaminAD3.drops)


        val success = db.insert(TBL_VITAD3, null, contentValues)
        db.close()
        return success
    }

    // Get all data from table Vitamin A+D3 and return it as a list of VitAD3Model
    @SuppressLint("Range")
    fun getAllVitAD3(): ArrayList<VitAD3Model> {
        val vitAD3List: ArrayList<VitAD3Model> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_VITAD3"
        val db = this.readableDatabase

        // Cursor is a pointer to a row in the table
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var density: Double
        var drops: Int

        // Move cursor to the first row
        if (cursor.moveToFirst()) {
            // Loop through the cursor while it is not at the end of the table
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID))
                density = cursor.getDouble(cursor.getColumnIndex(DENSITY))
                drops = cursor.getInt(cursor.getColumnIndex(DROPS))
                // Create a VitAModel object and add it to the list
                val vita = VitAD3Model(
                    id = id,
                    density = density,
                    drops = drops,
                )
                vitAD3List.add(vita)
            } while (cursor.moveToNext())
        }
        // Close the cursor and the database
        cursor.close()
        db.close()
        return vitAD3List
    }

    //isVitAD3Empty
    fun isVitAD3Empty(): Boolean {
        val db = this.readableDatabase
        val mCursor: Cursor? = db.rawQuery("SELECT * FROM $TBL_VITAD3", null)
        return mCursor!!.count > 0
    }


    // Insert data into table Oleje
    fun insertOleje(oleje: OlejeModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, oleje.id)
        contentValues.put(TYPE, oleje.type)
        contentValues.put(DENSITY, oleje.density)
        val success = db.insert(TBL_OLEJE, null, contentValues)
        db.close()
        return success
    }

    // Is Oleje table empty
    fun isOlejeEmpty(): Boolean {
        val db = this.readableDatabase
        val mCursor: Cursor? = db.rawQuery("SELECT * FROM $TBL_OLEJE", null)
        return mCursor!!.count > 0
    }

    //Get all data from table Oleje and return it as a list of OlejeModel
    @SuppressLint("Range")
    fun getAllOleje(): ArrayList<OlejeModel> {
        val olejeList: ArrayList<OlejeModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_OLEJE"
        val db = this.readableDatabase

        // Cursor is a pointer to a row in the table
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var type: String
        var density: Double

        // Move cursor to the first row
        if (cursor.moveToFirst()) {
            // Loop through the cursor while it is not at the end of the table
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID))
                type = cursor.getString(cursor.getColumnIndex(TYPE))
                density = cursor.getDouble(cursor.getColumnIndex(DENSITY))
                // Create a OlejeModel object and add it to the list
                val oleje = OlejeModel(
                    id = id,
                    type = type,
                    density = density,
                )
                olejeList.add(oleje)
            } while (cursor.moveToNext())
        }
        // Close the cursor and the database
        cursor.close()
        db.close()
        return olejeList
    }


    // Insert data into table Olejki
    fun insertOlejki(olejki: OlejkiModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, olejki.id)
        contentValues.put(TYPE, olejki.type)
        contentValues.put(DENSITY, olejki.density)
        contentValues.put(DROPS, olejki.drops)
        val success = db.insert(TBL_OLEJKI, null, contentValues)
        db.close()
        return success
    }

    // Is Olejki table empty
    fun isOlejkiEmpty(): Boolean {
        val db = this.readableDatabase
        val mCursor: Cursor? = db.rawQuery("SELECT * FROM $TBL_OLEJKI", null)
        return mCursor!!.count > 0
    }

    //get all data from table Olejki and return it as a list of OlejkiModel
    @SuppressLint("Range")
    fun getAllOlejki(): ArrayList<OlejkiModel> {
        val olejkiList: ArrayList<OlejkiModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_OLEJKI"
        val db = this.readableDatabase

        // Cursor is a pointer to a row in the table
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var type: String
        var density: Double
        var drops: Double

        // Move cursor to the first row
        if (cursor.moveToFirst()) {
            // Loop through the cursor while it is not at the end of the table
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID))
                type = cursor.getString(cursor.getColumnIndex(TYPE))
                density = cursor.getDouble(cursor.getColumnIndex(DENSITY))
                drops = cursor.getDouble(cursor.getColumnIndex(DROPS))
                // Create a OlejkiModel object and add it to the list
                val olejki = OlejkiModel(
                    id = id,
                    type = type,
                    density = density,
                    drops = drops
                )
                olejkiList.add(olejki)
            } while (cursor.moveToNext())
        }
        // Close the cursor and the database
        cursor.close()
        db.close()
        return olejkiList
    }


}