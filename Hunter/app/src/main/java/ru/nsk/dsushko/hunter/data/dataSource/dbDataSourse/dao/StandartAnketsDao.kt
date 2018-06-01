package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.dao

import android.arch.persistence.room.*
import ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities.EntityStandartAnketa
import ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities.EntityStudentAnketa

@Dao
interface StandartAnketsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg ankets: EntityStandartAnketa)

    @Delete
    fun delete(anketa: EntityStandartAnketa)

    @Query("SELECT * FROM EntityStandartAnketa")
    fun getAllEvents() : List<EntityStandartAnketa>

    @Query("SELECT COUNT(*) FROM EntityStandartAnketa")
    fun getCountAnkets() : Int
}