package com.jakubolszewski.kalkulatorwrecepturzedoz.database

data class AlcoholConcentrationModel(
    var id: Int,
    var alcohol_concentration: String = "",
    var alcohol_volume: Double = 0.0
)
