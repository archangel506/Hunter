package ru.nsk.dsushko.hunter.presentation.models

import java.io.Serializable


data class WorkFieldsInfo  (
        var id: Int?,
        var title: String?,
        var titleEng: String?,
        var description: String?,
        var hasVacancy: Boolean?
) : Serializable