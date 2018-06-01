package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class EntityWorkFields (
    @PrimaryKey
    var id: Long?,
    var title: String?,
    var titleEng: String?,
    var description: String?,
    var hasVacancy: Boolean?
)