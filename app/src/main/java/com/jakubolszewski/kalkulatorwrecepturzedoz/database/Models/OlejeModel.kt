package com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models

data class OlejeModel(
    var id: Int = 0,
    var type: String = "",
    var density: Double = 0.0,
    var drops: Int = 0,
)