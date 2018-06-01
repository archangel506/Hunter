package ru.nsk.dsushko.hunter.presentation.presenters

import ru.nsk.dsushko.hunter.presentation.models.EventInfo
import ru.nsk.dsushko.hunter.presentation.models.TechnologiesInfo
import ru.nsk.dsushko.hunter.presentation.models.WorkFieldsInfo

interface FormPresenter {
    fun openSettings()
    fun sendForm()
    fun updateWorkFields(workFieldsInfo: List<WorkFieldsInfo>)
    fun updateTechnologies(technologiesInfo: List<TechnologiesInfo>)
    fun updateEvents(events: List<EventInfo>)
}