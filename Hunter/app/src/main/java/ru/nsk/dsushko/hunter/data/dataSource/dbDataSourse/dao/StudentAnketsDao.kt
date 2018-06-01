package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.dao

import android.arch.persistence.room.*
import ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities.EntityStudentAnketa

@Dao
interface StudentAnketsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg ankets: EntityStudentAnketa)

    @Delete
    fun delete(anketa: EntityStudentAnketa)

    @Query("SELECT * FROM EntityStudentAnketa")
    fun getAllEvents() : List<EntityStudentAnketa>

    @Query("SELECT COUNT(*) FROM EntityStudentAnketa")
    fun getCountAnkets() : Int
}