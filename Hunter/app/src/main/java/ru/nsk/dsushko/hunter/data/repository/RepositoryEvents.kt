package ru.nsk.dsushko.hunter.data.repository

import android.content.Context
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.nsk.dsushko.hunter.data.dataSource.Convector
import ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.DbDataSourse
import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.InetDataSourse
import ru.nsk.dsushko.hunter.domain.Repository
import ru.nsk.dsushko.hunter.domain.models.Event

class RepositoryEvents(context: Context) : Repository {
    private val dbDataSourse = DbDataSourse(context)

    override fun getEvents() : Single<List<Event>> {
        val isHaveDb = dbDataSourse.isHaveEvents()

        return if(isHaveDb) dbDataSourse.getEvents().map { Convector.toEventsList(it) }
        else
        {
            val inetDataSourse = InetDataSourse()
            val single = inetDataSourse.getEvents()
            single.observeOn(Schedulers.io())
                    .subscribe(
                            {contributor -> dbDataSourse.saveEvents(contributor)},
                            { _ -> }
                    )

            return single.map { Convector.toEventsList(it) }
        }
    }
}
