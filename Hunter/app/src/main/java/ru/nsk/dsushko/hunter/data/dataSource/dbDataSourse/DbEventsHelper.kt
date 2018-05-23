package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models.EventAnswer


class DbEventsHelper(context: Context)
    : SQLiteOpenHelper(context, "hunter.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        if(db == null) return
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_VALUE_JSON + " TEXT NOT NULL,"
                + ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun isHaveEvents() : Boolean{
        val projection = arrayOf(TABLE_NAME)
        val cursor = readableDatabase.query(TABLE_NAME, projection,
                null, null, null, null, null)
        val answer = cursor.count > 0
        cursor.close()
        return answer
    }

    fun getEventsList() : List<EventAnswer> {
        val projection = arrayOf(TABLE_NAME)
        val cursor = readableDatabase.query(TABLE_NAME, projection,
                null, null, null, null, null)
        val idValue = cursor.getColumnIndex(COLUMN_VALUE_JSON)
        cursor.moveToFirst()
        val json = cursor.getString(idValue)
        val listType = object : TypeToken<List<EventAnswer>>(){}.type
        val answer = Gson().fromJson<List<EventAnswer>>(json, listType)
        cursor.close()
        return answer
    }

    fun saveEventsList( answer: List<EventAnswer>) {
        val contentValues = ContentValues()
        val gson = Gson()
        contentValues.put(COLUMN_VALUE_JSON, gson.toJson(answer))
        writableDatabase.delete(TABLE_NAME, null, null)
        writableDatabase.insert(TABLE_NAME, null, contentValues)
        writableDatabase.close()
    }

    private companion object MarkingTableEvents : BaseColumns {
        const val TABLE_NAME = "events"
        const val ID = BaseColumns._ID
        const val COLUMN_VALUE_JSON = "value"
    }


}