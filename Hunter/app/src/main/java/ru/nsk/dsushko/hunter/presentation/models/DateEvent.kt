package ru.nsk.dsushko.hunter.presentation.models

import java.io.Serializable


data class DateEvent(
    var start: String?,
    var end: String?
) : Serializable
