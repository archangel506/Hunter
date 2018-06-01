package ru.nsk.dsushko.hunter.data.repository

import android.content.Context
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.EventsDatabase
import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.InetDataSourse
import ru.nsk.dsushko.hunter.domain.Repository
import ru.nsk.dsushko.hunter.domain.models.*

class RepositoryEvents(context: Context) : Repository {
    private val inetDataSourse = InetDataSourse()
    private val studentAnketsDao = EventsDatabase.getInstance(context).studentAnketsDao()
    private val standartAnketsDao = EventsDatabase.getInstance(context).standartAnketsDao()
    private val eventsAnketsDao = EventsDatabase.getInstance(context).eventsDao()
    private val workFieldsDao = EventsDatabase.getInstance(context).workFieldsDao()
    private val technologiesDao = EventsDatabase.getInstance(context).technologiesDao()

    override fun getTechnologies(): Single<List<Technologies>> {
        val haveAnkets = technologiesDao.getCountTechnologies()!= 0

        return if(haveAnkets) {
            Single.just(technologiesDao.getAllTechnologies()).map { RepositoryConverter.toTechnologiesListFromEntity(it) }
        }
        else
        {
            return getActualTechnologies()
        }
    }

    fun getActualTechnologies(): Single<List<Technologies>> {
        val single = inetDataSourse.getTechnologies()
        single.subscribe(
                {
                    workFields ->
                    run {
                        technologiesDao.deleteAll()
                        for(workField in RepositoryConverter.toEntityTechnologiesList(workFields)) {
                            technologiesDao.insertAll(workField)
                        }
                    }
                },
                { _ -> }
        )
        return single.map { RepositoryConverter.toTechnologiesList(it) }
    }

    override fun getEvents() : Single<List<Event>> {

        val haveAnkets = eventsAnketsDao.getCountEvents() != 0

        return if(haveAnkets) {
            Single.just(eventsAnketsDao.getAllEvents()).map { RepositoryConverter.toEventsListFromEntity(it) }
        }
        else
        {
            return getActualEvents()
        }
    }

    override fun getActualEvents(): Single<List<Event>>{
        getActualTechnologies()
        getActualWorkFields()
        val single = inetDataSourse.getEvents()
        single.observeOn(Schedulers.io())
                .subscribe(
                        {

                            events ->
                            run {
                                eventsAnketsDao.deleteAll()
                                for (event in RepositoryConverter.toEntityEventsListFromAnswer(events)) {
                                    eventsAnketsDao.insertAll(event)
                                }

                            }
                        },
                        { _ -> }
                )

        return single.map { RepositoryConverter.toEventsList(it) }
    }

    override fun getWorkFields(): Single<List<WorkFields>> {
        val haveWorkFields = workFieldsDao.getCountWorkFields() != 0
        return if(haveWorkFields)
            Single.just(workFieldsDao.getAllWorkFields())
                    .map { RepositoryConverter.toWorkFieldsListFromEntity(it)}
        else getActualWorkFields()

    }

     fun getActualWorkFields(): Single<List<WorkFields>>{
        val single = inetDataSourse.getWorkFields()
        single.subscribe(
                {
                    workFields ->
                    run {
                        workFieldsDao.deleteAll()
                        for(workField in RepositoryConverter.toEntityWorkFieldsList(workFields)) {
                            workFieldsDao.insertAll(workField)
                        }
                    }
                },
                { _ -> }
        )
        return single.map { RepositoryConverter.toWorkFieldsList(it) }
    }

    override fun sendAnketa(anketa: StandartAnketa){
        val forSendAnketa = RepositoryConverter.toBodyStandartAnketa(anketa)
        val forEntityAnketa = RepositoryConverter.toEntityStandartAnketa(anketa)
        val response = inetDataSourse.sendAnketa(forSendAnketa)
        response.subscribe(
                {_ -> },
                {_ -> standartAnketsDao.insertAll(forEntityAnketa)}
        )
    }

    override fun sendAnketa(anketa: StudentAnketa) {
        val convectAnketa = RepositoryConverter.toBodyStudentAnketa(anketa)
        val response = inetDataSourse.sendAnketa(convectAnketa)
        val forEntityAnketa = RepositoryConverter.toEntityStudentAnketa(anketa)
        response.subscribe(
                 {_ -> },
                {_ -> studentAnketsDao.insertAll(forEntityAnketa)}
        )
    }

    override fun getCountStandartAnkets() = standartAnketsDao.getCountAnkets()

    override fun getCountStudentAnkets() = studentAnketsDao.getCountAnkets()

    override fun resendAnkets() {
        for(anketa in standartAnketsDao.getAllEvents()){
            inetDataSourse.sendAnketa(RepositoryConverter.toBodyStandartAnketa(anketa)).subscribe(
                    {answer ->
                        if(answer.result == true){
                           standartAnketsDao.delete(anketa)
                        }
                    },
                    { _ -> }
            )
        }

        for(anketa in studentAnketsDao.getAllEvents()){
            inetDataSourse.sendAnketa(RepositoryConverter.toBodyStudentAnketa(anketa)).subscribe(
                    {answer ->
                        if(answer.result == true){
                            studentAnketsDao.delete(anketa)
                        }
                    },
                    { _ -> }
            )
        }
    }
}
