package ru.nsk.dsushko.hunter.presentation.models

data class EventPresent(
        var id: Int?,
        var title: String?,
        var cities: List<CityPresent>?,
        var description: String?,
        var format: Int?,
        var date: DatePresent?,
        var cardImage: String?,
        var status: Int?,
        var iconStatus: String?,
        var eventFormat: String?,
        var eventFormatEng: String?
)
