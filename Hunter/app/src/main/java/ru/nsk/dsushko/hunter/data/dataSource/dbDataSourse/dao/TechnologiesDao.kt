package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.dao

import android.arch.persistence.room.*
import ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities.EntityTechnologies


@Dao
interface TechnologiesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg technologies: EntityTechnologies)

    @Delete
    fun delete(technologies: EntityTechnologies)

    @Query("DELETE FROM EntityTechnologies")
    fun deleteAll()

    @Query("SELECT * FROM EntityTechnologies")
    fun getAllTechnologies() : List<EntityTechnologies>

    @Query("SELECT COUNT(*) FROM EntityTechnologies")
    fun getCountTechnologies() : Int
}