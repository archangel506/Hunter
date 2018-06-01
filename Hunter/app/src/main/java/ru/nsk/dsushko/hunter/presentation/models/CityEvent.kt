package ru.nsk.dsushko.hunter.presentation.models

import java.io.Serializable


data class CityEvent(
    var id: Int?,
    var nameRus: String?,
    var nameEng: String?,
    var icon: String?,
    var isActive: Boolean?
) : Serializable
