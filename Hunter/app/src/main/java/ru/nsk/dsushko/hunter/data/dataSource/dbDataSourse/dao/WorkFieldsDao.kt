package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.dao

import android.arch.persistence.room.*
import ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities.EntityWorkFields

@Dao
interface WorkFieldsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg workFields: EntityWorkFields)

    @Delete
    fun delete(event: EntityWorkFields)

    @Query("DELETE FROM EntityWorkFields")
    fun deleteAll()

    @Query("SELECT * FROM EntityWorkFields")
    fun getAllWorkFields() : List<EntityWorkFields>

    @Query("SELECT COUNT(*) FROM EntityWorkFields")
    fun getCountWorkFields() : Int

}