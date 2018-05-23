package ru.nsk.dsushko.hunter.presentation.models

import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models.CityAnswer
import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models.DateAnswer
import ru.nsk.dsushko.hunter.domain.models.City
import ru.nsk.dsushko.hunter.domain.models.Date
import ru.nsk.dsushko.hunter.domain.models.Event

object Convector{
    fun toEventsPresentList(events: List<Event>) : List<EventPresent> {
        val convertEvents = mutableListOf<EventPresent>()
        for(event in events){
            convertEvents.add(toEventPresent(event))
        }
        return convertEvents.toList()
    }

    private fun toEventPresent(event: Event)
            = EventPresent(event.id,
            event.title,
            toCityPresentList(event.cities),
            event.description,
            event.format,
            toDatePresent(event.date),
            event.cardImage,
            event.status,
            event.iconStatus,
            event.eventFormat,
            event.eventFormatEng)

    private fun toCityPresentList(cities: List<City>?) : List<CityPresent>?{
        if(cities == null) return null
        val list = mutableListOf<CityPresent>()
        for(city in cities){
            list.add(toCityPresent(city))
        }
        return list.toList()
    }

    private fun toCityPresent(city: City) =
            CityPresent(city.id,
                    city.nameRus,
                    city.nameEng,
                    city.icon,
                    city.isActive)

    private fun toDatePresent(data : Date?) : DatePresent?
            = if(data == null) null
            else DatePresent(data.start,
            data.end)

}