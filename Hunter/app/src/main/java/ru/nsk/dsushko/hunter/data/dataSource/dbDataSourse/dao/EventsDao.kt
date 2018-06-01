package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.dao

import android.arch.persistence.room.*
import ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities.EntityEvent

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg events: EntityEvent)

    @Delete
    fun delete(event: EntityEvent)

    @Query("DELETE FROM EntityEvent")
    fun deleteAll()

    @Query("SELECT * FROM EntityEvent")
    fun getAllEvents() : List<EntityEvent>

    @Query("SELECT COUNT(*) FROM EntityEvent")
    fun getCountEvents() : Int
}