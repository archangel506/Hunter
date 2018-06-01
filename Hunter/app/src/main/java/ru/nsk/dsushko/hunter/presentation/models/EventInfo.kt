package ru.nsk.dsushko.hunter.presentation.models

import java.io.Serializable

data class EventInfo(
        var id: Int?,
        var title: String?,
        var cities: List<CityEvent>?,
        var description: String?,
        var format: Int?,
        var date: DateEvent?,
        var cardImage: String?,
        var status: Int?,
        var iconStatus: String?,
        var eventFormat: String?,
        var eventFormatEng: String?
) : Serializable
