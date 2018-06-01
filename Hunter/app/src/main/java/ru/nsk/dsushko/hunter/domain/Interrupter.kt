package ru.nsk.dsushko.hunter.domain

import android.content.Context
import ru.nsk.dsushko.hunter.domain.models.StandartAnketa
import ru.nsk.dsushko.hunter.domain.models.StudentAnketa

class Interrupter(context: Context){
    private val repository = FactoryRepository.create(context)

    fun getEvents() = repository.getEvents()
    fun getActualEvents() = repository.getActualEvents()
    fun sendAnketa(anketa: StandartAnketa) = repository.sendAnketa(anketa)
    fun sendAnketa(anketa: StudentAnketa) = repository.sendAnketa(anketa)
    fun getCountAnkets() = repository.getCountStandartAnkets() + repository.getCountStudentAnkets()
    fun resendAnkets() = repository.resendAnkets()
    fun getWorkFields() = repository.getWorkFields()
    fun getTechnologies() = repository.getTechnologies()
}
