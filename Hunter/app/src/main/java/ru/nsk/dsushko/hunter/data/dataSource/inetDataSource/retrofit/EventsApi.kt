package ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models.*


interface EventsApi {
    @GET("api/v1/Events/show-anketa/")
    fun getEvents(): Single<List<EventAnswer>>
    @POST("api/v1/Anketa/standard")
    fun sendStandartAnketa(@Body anketa: BodyStandartAnketa) : Single<ResponseAnketa>
    @POST("api/v1/Anketa/student")
    fun sendStudentAnketa(@Body anketa: BodyStudentAnketa) : Single<ResponseAnketa>
    @GET("/api/v1/WorkFields/all")
    fun getWorkFields() : Single<List<WorkFieldsAnswer>>
    @GET("/api/v1/Tags/anketa")
    fun  getTechnologies() : Single<List<TechnologiesAnswer>>
}