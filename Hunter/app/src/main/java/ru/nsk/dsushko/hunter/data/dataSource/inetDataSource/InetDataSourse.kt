package ru.nsk.dsushko.hunter.data.dataSource.inetDataSource

import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.EventsApi
import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.RetrofitBuilder

class InetDataSourse() {
    private val eventsApi: EventsApi

    init {
        val retrofit = RetrofitBuilder.build()
        eventsApi = retrofit.create(EventsApi::class.java)
    }

    fun getEvents() = eventsApi.getEvents()
}
