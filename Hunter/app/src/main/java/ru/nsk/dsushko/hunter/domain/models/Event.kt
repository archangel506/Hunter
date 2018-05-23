package ru.nsk.dsushko.hunter.domain.models

import ru.nsk.dsushko.hunter.presentation.models.CityPresent
import ru.nsk.dsushko.hunter.presentation.models.DatePresent

data class Event(
        var id: Int?,
        var title: String?,
        var cities: List<City>?,
        var description: String?,
        var format: Int?,
        var date: Date?,
        var cardImage: String?,
        var status: Int?,
        var iconStatus: String?,
        var eventFormat: String?,
        var eventFormatEng: String?
)
