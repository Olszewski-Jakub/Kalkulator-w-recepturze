package com.jakubolszewski.kalkulatorwrecepturzedoz.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

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

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblVitA = ("CREATE TABLE " + TBL_VITA + " ("
                + ID + " INTEGER PRIMARY KEY,"
                + COMPANY + " TEXT,"
                + DENSITY + " NUMERIC,"
                + DROPS + " INTEGER,"
                + MASS_UNITS + " NUMERIC " + ")")
        db?.execSQL(createTblVitA)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_VITA")
        onCreate(db)
    }

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

    @SuppressLint("Range")
    fun getAllVitA(): ArrayList<VitAModel>{
        val vitAList: ArrayList<VitAModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_VITA"
        val db = this.readableDatabase

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


        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID))
                company = cursor.getString(cursor.getColumnIndex(COMPANY))
                density = cursor.getDouble(cursor.getColumnIndex(DENSITY))
                drops = cursor.getInt(cursor.getColumnIndex(DROPS))
                mass_units = cursor.getDouble(cursor.getColumnIndex(MASS_UNITS))

                val vita = VitAModel(id = id, company = company, density = density, drops = drops, mass_units = mass_units)
                vitAList.add(vita)
            }while (cursor.moveToNext())
        }

        return vitAList
    }


}