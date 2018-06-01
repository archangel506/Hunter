package ru.nsk.dsushko.hunter.domain

import io.reactivex.Single
import ru.nsk.dsushko.hunter.domain.models.*


interface Repository {
    fun getEvents() : Single<List<Event>>
    fun getActualEvents(): Single<List<Event>>
    fun sendAnketa(anketa: StandartAnketa)
    fun sendAnketa(anketa: StudentAnketa)
    fun getCountStudentAnkets() : Int
    fun getCountStandartAnkets() : Int
    fun resendAnkets()
    fun getWorkFields() : Single<List<WorkFields>>
    fun getTechnologies(): Single<List<Technologies>>
}