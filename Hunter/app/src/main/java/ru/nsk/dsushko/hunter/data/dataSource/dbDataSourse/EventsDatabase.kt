package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.dao.*
import ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities.*

@Database(
        entities = [
            (EntityCity::class),
            (EntityDate::class),
            (EntityEvent::class),
            (EntityStandartAnketa::class),
            (EntityStudentAnketa::class),
            (EntityWorkFields::class),
            (EntityTechnologies::class)
        ],
        version = 1,
        exportSchema = false
)
@TypeConverters(
        TypeConverterCities::class,
        TypeConvecterListInt::class
)
abstract class EventsDatabase : RoomDatabase(){
    abstract fun eventsDao() : EventsDao
    abstract fun standartAnketsDao() : StandartAnketsDao
    abstract fun studentAnketsDao() : StudentAnketsDao
    abstract fun workFieldsDao() : WorkFieldsDao
    abstract fun technologiesDao() : TechnologiesDao

    companion object {
        private var INSTANCE: EventsDatabase? = null

        fun getInstance(context: Context): EventsDatabase {
            if (INSTANCE == null) {
                synchronized(EventsDatabase::class) {
                    val temp = Room.databaseBuilder(context,
                            EventsDatabase::class.java, "events.db")
                    INSTANCE = temp.build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}