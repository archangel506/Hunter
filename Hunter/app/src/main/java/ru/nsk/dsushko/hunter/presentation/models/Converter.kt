package ru.nsk.dsushko.hunter.presentation.models

import ru.nsk.dsushko.hunter.domain.models.*

object Converter{
    fun toEventsPresentList(events: List<Event>) : List<EventInfo> {
        val convertEvents = mutableListOf<EventInfo>()
        for(event in events){
            convertEvents.add(toEventPresent(event))
        }
        return convertEvents.toList()
    }

    fun toStandartAnketa(anketa: StandartAnketaInfo) = StandartAnketa(
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

    fun toStudentAnketa(anketa: StudentAnketaInfo) = StudentAnketa(
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

    fun toTechnologiesInfoList(technologies: List<Technologies>) : List<TechnologiesInfo> {
        val technolotyInfo = mutableListOf<TechnologiesInfo>()
        for(technology in technologies){
            technolotyInfo.add(toTechologies(technology))
        }
        return technolotyInfo.toList()
    }

    fun toWorkFieldsInfoList(workFields : List<WorkFields>) : List<WorkFieldsInfo> {
        val workFieldsInfo = mutableListOf<WorkFieldsInfo>()
        for(workField in workFields){
            workFieldsInfo.add(toWorkFieldsInfo(workField))
        }
        return workFieldsInfo.toList()
    }

    private fun toWorkFieldsInfo(workFields: WorkFields)
            = WorkFieldsInfo(
            workFields.id,
            workFields.title,
            workFields.titleEng,
            workFields.description,
            workFields.hasVacancy
    )

    private fun toTechologies(technology: Technologies)
            = TechnologiesInfo(
            technology.id,
            technology.title
    )

    private fun toEventPresent(event: Event)
            = EventInfo(event.id,
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

    private fun toCityPresentList(cities: List<City>?) : List<CityEvent>?{
        if(cities == null) return null
        val list = mutableListOf<CityEvent>()
        for(city in cities){
            list.add(toCityPresent(city))
        }
        return list.toList()
    }

    private fun toCityPresent(city: City) =
            CityEvent(city.id,
                    city.nameRus,
                    city.nameEng,
                    city.icon,
                    city.isActive)

    private fun toDatePresent(data : Date?) : DateEvent?
            = if(data == null) null
            else DateEvent(data.start,
            data.end)

}