package com.jakubolszewski.kalkulatorwrecepturzedoz.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Object with all the columns names
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "KWK.db"
        private const val ID = "id"
        private const val TBL_VITA = "tbl_vit_a"
        private const val COMPANY = "company"
        private const val DENSITY = "density"
        private const val DROPS = "drops"
        private const val MASS_UNITS = "mass_units"

    }
    //Create table SQL query
    override fun onCreate(db: SQLiteDatabase?) {
        val createTblVitA = ("CREATE TABLE " + TBL_VITA + " ("
                + ID + " INTEGER PRIMARY KEY,"
                + COMPANY + " TEXT,"
                + DENSITY + " NUMERIC,"
                + DROPS + " INTEGER,"
                + MASS_UNITS + " NUMERIC " + ")")
        db?.execSQL(createTblVitA)
    }
    //Upgrade table SQL query
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_VITA")
        onCreate(db)
    }
    // Insert data into table
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
    //Update data in table VitA
    fun updateVitA(vitA: VitAModel) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, vitA.id)
        contentValues.put(COMPANY, vitA.company)
        contentValues.put(DENSITY, vitA.density)
        contentValues.put(DROPS, vitA.drops)
        contentValues.put(MASS_UNITS, vitA.mass_units)

        db.update(TBL_VITA, contentValues,"id=?", arrayOf(vitA.id.toString()))
        db.close()

    }

    // Get all data from table VitA and return it as a list of VitAModel
    @SuppressLint("Range")
    fun getAllVitA(): ArrayList<VitAModel>{
        val vitAList: ArrayList<VitAModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_VITA"
        val db = this.readableDatabase

        // Cursor is a pointer to a row in the table
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery,null)

        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id:Int
        var company: String
        var density:Double
        var drops: Int
        var mass_units: Double

        // Move cursor to the first row
        if (cursor.moveToFirst()){
            // Loop through the cursor while it is not at the end of the table
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID))
                company = cursor.getString(cursor.getColumnIndex(COMPANY))
                density = cursor.getDouble(cursor.getColumnIndex(DENSITY))
                drops = cursor.getInt(cursor.getColumnIndex(DROPS))
                mass_units = cursor.getDouble(cursor.getColumnIndex(MASS_UNITS))
                // Create a VitAModel object and add it to the list
                val vita = VitAModel(id = id, company = company, density = density, drops = drops, mass_units = mass_units)
                vitAList.add(vita)
            }while (cursor.moveToNext())
        }
        // Close the cursor and the database
        cursor.close()
        db.close()
        return vitAList
    }


}