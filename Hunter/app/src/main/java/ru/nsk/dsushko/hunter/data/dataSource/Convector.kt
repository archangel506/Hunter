package ru.nsk.dsushko.hunter.data.dataSource

import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models.CityAnswer
import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models.DateAnswer
import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models.EventAnswer
import ru.nsk.dsushko.hunter.domain.models.City
import ru.nsk.dsushko.hunter.domain.models.Date
import ru.nsk.dsushko.hunter.domain.models.Event

object Convector{
    fun toEventsList(events: List<EventAnswer>) : List<Event> {
        val convertEvents = mutableListOf<Event>()
        for(event in events){
            convertEvents.add(toEvent(event))
        }
        return convertEvents.toList()
    }

    private fun toEvent(event: EventAnswer)
            = Event(event.id,
            event.title,
            toCityList(event.cities),
            event.description,
            event.format,
            toData(event.date),
            event.cardImage,
            event.status,
            event.iconStatus,
            event.eventFormat,
            event.eventFormatEng)

    private fun toCityList(cities: List<CityAnswer>?) : List<City>?{
        if(cities == null) return null
        val list = mutableListOf<City>()
        for(city in cities){
            list.add(toCity(city))
        }
        return list.toList()
    }

    private fun toCity(city: CityAnswer) =
            City(city.id,
                    city.nameRus,
                    city.nameEng,
                    city.icon,
                    city.isActive)

    private fun toData(data : DateAnswer?) : Date?
            = if(data == null) null
            else Date(data.start,
            data.end)

}