package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class EntityDate (
        @SerializedName("start")
        @Expose
        var start: String?,
        @SerializedName("end")
        @Expose
        var end: String?
)
{
        @PrimaryKey(autoGenerate = true)
        var dateId : Long? = null
}