package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class EntityCity (
        @PrimaryKey
        @SerializedName("cityId")
        @Expose
        var id: Long?,
        @SerializedName("nameRus")
        @Expose
        var nameRus: String?,
        @SerializedName("nameEng")
        @Expose
        var nameEng: String?,
        @SerializedName("icon")
        @Expose
        var icon: String?,
        @SerializedName("isActive")
        @Expose
        var isActive: Boolean?
)

