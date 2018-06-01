package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverterCities {


        @TypeConverter
        fun fromCities(cities: List<EntityCity>?): String? {
            val gson = Gson()
            return gson.toJson(cities)
        }

        @TypeConverter
        fun toCities(json: String?): List<EntityCity>? {
            val listType = object : TypeToken<List<EntityCity>>() {}.type
            return Gson().fromJson<List<EntityCity>>(json, listType)
        }

}