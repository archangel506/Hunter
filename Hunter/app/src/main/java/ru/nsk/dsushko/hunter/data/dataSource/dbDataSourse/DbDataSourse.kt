package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse

import android.content.Context
import io.reactivex.Single
import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models.EventAnswer

class DbDataSourse(context: Context){
    private val dbEventsHelper = DbEventsHelper(context)
    fun getEvents() = Single.just(dbEventsHelper.getEventsList())!!
    fun isHaveEvents() = dbEventsHelper.isHaveEvents()
    fun saveEvents(events :List<EventAnswer>) = dbEventsHelper.saveEventsList(events)
}
