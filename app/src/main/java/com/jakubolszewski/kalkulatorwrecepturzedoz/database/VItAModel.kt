package com.jakubolszewski.kalkulatorwrecepturzedoz.database
data class VitAModel(
    var id: Int = 0,
    var company: String = "",
    var density:Double = 0.0,
    var drops:Int = 0,
    var mass_units:Double = 0.0
    )
