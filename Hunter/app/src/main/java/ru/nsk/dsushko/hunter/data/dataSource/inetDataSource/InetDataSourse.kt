package ru.nsk.dsushko.hunter.data.dataSource.inetDataSource

import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.EventsApi
import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.RetrofitBuilder
import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models.BodyStandartAnketa
import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models.BodyStudentAnketa

class InetDataSourse() {
    private val eventsApi: EventsApi

    init {
        val retrofit = RetrofitBuilder.build()
        eventsApi = retrofit.create(EventsApi::class.java)
    }

    fun getEvents() = eventsApi.getEvents()
    fun sendAnketa(anketa: BodyStandartAnketa) = eventsApi.sendStandartAnketa(anketa)
    fun sendAnketa(anketa: BodyStudentAnketa) = eventsApi.sendStudentAnketa(anketa)
    fun getWorkFields() = eventsApi.getWorkFields()
    fun getTechnologies() = eventsApi.getTechnologies()
}
