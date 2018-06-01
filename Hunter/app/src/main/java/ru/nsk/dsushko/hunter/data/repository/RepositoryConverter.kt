package ru.nsk.dsushko.hunter.data.repository

import ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities.*
import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models.*
import ru.nsk.dsushko.hunter.domain.models.*

object RepositoryConverter{
    fun toEventsList(events: List<EventAnswer>) : List<Event> {
        val convertEvents = mutableListOf<Event>()
        for(event in events){
            convertEvents.add(toEvent(event))
        }
        return convertEvents.toList()
    }

    fun toEventsListFromEntity(events: List<EntityEvent>) : List<Event> {
        val convertEvents = mutableListOf<Event>()
        for(event in events){
            convertEvents.add(toEvent(event))
        }
        return convertEvents.toList()
    }

    fun toEntityEventsListFromAnswer(events: List<EventAnswer>) : List<EntityEvent> {
        val convertEvents = mutableListOf<EntityEvent>()
        for(event in events){
            convertEvents.add(toEntityEvent(event))
        }
        return convertEvents.toList()
    }

    fun toBodyStandartAnketa(anketa: StandartAnketa)
            = BodyStandartAnketa(
            anketa.company,
            anketa.position,
            anketa.workfieldId,
            anketa.likeReports,
            anketa.suggestions,
            anketa.technologiesId,
            anketa.otherTechnologies,
            anketa.interestingEventIds,
            anketa.isReadyToBeSpeaker,
            anketa.eventId,
            anketa.city,
            anketa.isSubscribe,
            anketa.name,
            anketa.phone,
            anketa.email,
            anketa.isAgree
    )

    fun toBodyStandartAnketa(anketa: EntityStandartAnketa)
            = BodyStandartAnketa(
            anketa.company,
            anketa.position,
            anketa.workfieldId,
            anketa.likeReports,
            anketa.suggestions,
            anketa.technologiesId,
            anketa.otherTechnologies,
            anketa.interestingEventIds,
            anketa.isReadyToBeSpeaker,
            anketa.eventId,
            anketa.city,
            anketa.isSubscribe,
            anketa.name,
            anketa.phone,
            anketa.email,
            anketa.isAgree
    )

    fun toEntityStandartAnketa(anketa: StandartAnketa)
            = EntityStandartAnketa(
            anketa.company,
            anketa.position,
            anketa.workfieldId,
            anketa.likeReports,
            anketa.suggestions,
            anketa.technologiesId,
            anketa.otherTechnologies,
            anketa.interestingEventIds,
            anketa.isReadyToBeSpeaker,
            anketa.eventId,
            anketa.city,
            anketa.isSubscribe,
            anketa.name,
            anketa.phone,
            anketa.email,
            anketa.isAgree
    )

    fun toBodyStudentAnketa(anketa: StudentAnketa)
            = BodyStudentAnketa(
            anketa.school,
            anketa.faculty,
            anketa.speciality,
            anketa.graduateYear,
            anketa.interestingWorkfieldIds,
            anketa.interestingEventIds,
            anketa.comment,
            anketa.eventId,
            anketa.city,
            anketa.isSubscribe,
            anketa.name,
            anketa.phone,
            anketa.email,
            anketa.isAgree
    )

    fun toBodyStudentAnketa(anketa: EntityStudentAnketa)
            = BodyStudentAnketa(
            anketa.school,
            anketa.faculty,
            anketa.speciality,
            anketa.graduateYear,
            anketa.interestingWorkfieldIds,
            anketa.interestingEventIds,
            anketa.comment,
            anketa.eventId,
            anketa.city,
            anketa.isSubscribe,
            anketa.name,
            anketa.phone,
            anketa.email,
            anketa.isAgree
    )

    fun toEntityStudentAnketa(anketa: StudentAnketa)
            = EntityStudentAnketa(
            anketa.school,
            anketa.faculty,
            anketa.speciality,
            anketa.graduateYear,
            anketa.interestingWorkfieldIds,
            anketa.interestingEventIds,
            anketa.comment,
            anketa.eventId,
            anketa.city,
            anketa.isSubscribe,
            anketa.name,
            anketa.phone,
            anketa.email,
            anketa.isAgree
    )

    fun toWorkFieldsListFromEntity(workFields: List<EntityWorkFields>) : List<WorkFields> {
        val convertEvents = mutableListOf<WorkFields>()
        for (workField in workFields) {
            convertEvents.add(toWorkFields(workField))
        }
        return convertEvents.toList()
    }

    fun toWorkFieldsList(workFields: List<WorkFieldsAnswer>) : List<WorkFields> {
        val convertEvents = mutableListOf<WorkFields>()
        for (workField in workFields) {
            convertEvents.add(toWorkFields(workField))
        }
        return convertEvents.toList()
    }

    fun toEntityWorkFieldsList(workFields: List<WorkFieldsAnswer>) : List<EntityWorkFields> {
        val convertEvents = mutableListOf<EntityWorkFields>()
        for (workField in workFields) {
            convertEvents.add(toEntityWorkFields(workField))
        }
        return convertEvents.toList()
    }

    fun toTechnologiesListFromEntity(technologies: List<EntityTechnologies>) : List<Technologies> {
        val convertEvents = mutableListOf<Technologies>()
        for (technology in technologies) {
            convertEvents.add(toTechologies(technology))
        }
        return convertEvents.toList()
    }

    fun toTechnologiesList(technologies: List<TechnologiesAnswer>) : List<Technologies> {
        val convertEvents = mutableListOf<Technologies>()
        for (technology in technologies) {
            convertEvents.add(toTechologies(technology))
        }
        return convertEvents.toList()
    }

    fun toEntityTechnologiesList(technologies: List<TechnologiesAnswer>) : List<EntityTechnologies> {
        val convertEvents = mutableListOf<EntityTechnologies>()
        for (technology in technologies) {
            convertEvents.add(toEntityTechnologies(technology))
        }
        return convertEvents.toList()
    }

    private fun toWorkFields(workFields: EntityWorkFields)
            = WorkFields(
            workFields.id?.toInt(),
            workFields.title,
            workFields.titleEng,
            workFields.description,
            workFields.hasVacancy
    )

    private fun toWorkFields(workFields: WorkFieldsAnswer)
            = WorkFields(
            workFields.id,
            workFields.title,
            workFields.titleEng,
            workFields.description,
            workFields.hasVacancy
    )

    private fun toEntityWorkFields(workFields: WorkFieldsAnswer)
            = EntityWorkFields(
            workFields.id?.toLong(),
            workFields.title,
            workFields.titleEng,
            workFields.description,
            workFields.hasVacancy
    )

    private fun toTechologies(techologies: TechnologiesAnswer)
            = Technologies(
            techologies.id,
            techologies.title
    )

    private fun toTechologies(techologies: EntityTechnologies)
            = Technologies(
            techologies.id?.toInt(),
            techologies.title
    )

    private fun toEntityTechnologies(techologies: TechnologiesAnswer)
            = EntityTechnologies(
            techologies.id?.toLong(),
            techologies.title
    )

    private fun toEvent(event: EventAnswer)
            = Event(event.id,
            event.title,
            toCityList(event.cities),
            event.description,
            event.format,
            toDate(event.date),
            event.cardImage,
            event.status,
            event.iconStatus,
            event.eventFormat,
            event.eventFormatEng)

    private fun toEvent(event: EntityEvent)
            = Event(event.id?.toInt(),
            event.title,
            toCityListDb(event.cities),
            event.description,
            event.format,
            toDate(event.date),
            event.cardImage,
            event.status,
            event.iconStatus,
            event.eventFormat,
            event.eventFormatEng)

    private fun toEntityEvent(event: EventAnswer)
            = EntityEvent(event.id?.toLong(),
            event.title,
            toEntityCityList(event.cities),
            event.description,
            event.format,
            toEntityDate(event.date),
            event.cardImage,
            event.status,
            event.iconStatus,
            event.eventFormat,
            event.eventFormatEng)

    private fun toCityListDb(cities: List<EntityCity>?) : List<City>?{
        if(cities == null) return null
        val list = mutableListOf<City>()
        for(city in cities){
            list.add(toCity(city))
        }
        return list.toList()
    }

    private fun toCityList(cities: List<CityAnswer>?) : List<City>?{
        if(cities == null) return null
        val list = mutableListOf<City>()
        for(city in cities){
            list.add(toCity(city))
        }
        return list.toList()
    }

    private fun toEntityCityList(cities: List<CityAnswer>?) : List<EntityCity>?{
        if(cities == null) return null
        val list = mutableListOf<EntityCity>()
        for(city in cities){
            list.add(toEntityCity(city))
        }
        return list.toList()
    }


    private fun toCity(city: CityAnswer) =
            City(city.id,
                    city.nameRus,
                    city.nameEng,
                    city.icon,
                    city.isActive)

    private fun toCity(city: EntityCity) =
            City(city.id?.toInt(),
                    city.nameRus,
                    city.nameEng,
                    city.icon,
                    city.isActive)

    private fun toEntityCity(city: CityAnswer) =
            EntityCity(city.id?.toLong(),
                    city.nameRus,
                    city.nameEng,
                    city.icon,
                    city.isActive)

    private fun toDate(data : DateAnswer?) : Date?
            = if(data == null) null
            else Date(data.start,
            data.end)

    private fun toDate(data : EntityDate?) : Date?
            = if(data == null) null
    else Date(data.start,
            data.end)

    private fun toEntityDate(data : DateAnswer?) : EntityDate?
            = if(data == null) null
    else EntityDate(data.start,
            data.end)

}