package ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit

import io.reactivex.Single
import retrofit2.http.GET
import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models.EventAnswer


interface EventsApi {
    @GET("/api/v1/EventPresent/show-anketa")
    fun getEvents(): Single<List<EventAnswer>>
}