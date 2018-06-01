package ru.nsk.dsushko.hunter.presentation.models

import java.io.Serializable


data class TechnologiesInfo (
        var id: Int?,
        var title: String? = null
)  : Serializable