package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class EntityTechnologies(
        @PrimaryKey
        var id: Long?,
        var title: String? = null
)