package ru.nsk.dsushko.hunter.domain.models



data class WorkFields (
    var id: Int?,
    var title: String?,
    var titleEng: String?,
    var description: String?,
    var hasVacancy: Boolean?
)