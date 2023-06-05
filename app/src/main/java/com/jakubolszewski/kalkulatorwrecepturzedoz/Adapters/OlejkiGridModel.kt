package com.jakubolszewski.kalkulatorwrecepturzedoz.Adapters

data class OlejkiGridModel(
    val main_olejek: String,
    val main_olejke2: String,
    var mass: String = "",
    var volume: String = "",
    var drops: String = "",
)
