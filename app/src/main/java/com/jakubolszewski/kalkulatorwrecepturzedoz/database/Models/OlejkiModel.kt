package com.jakubolszewski.kalkulatorwrecepturzedoz.database.Models

data class OlejkiModel(
    var id: Int = 0,
    var type: String = "",
    var density: Double = 0.0,
    var drops: Double = 0.0,
)
