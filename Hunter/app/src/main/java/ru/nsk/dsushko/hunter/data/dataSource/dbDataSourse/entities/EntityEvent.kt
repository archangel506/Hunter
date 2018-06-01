package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities

import android.arch.persistence.room.*

@Entity
data class EntityEvent(
        @PrimaryKey
        var id: Long?,
        var title: String?,
        var cities: List<EntityCity>?,
        var description: String?,
        var format: Int?,
        @Embedded
        var date: EntityDate?,
        var cardImage: String?,
        var status: Int?,
        var iconStatus: String?,
        var eventFormat: String?,
        var eventFormatEng: String?
)