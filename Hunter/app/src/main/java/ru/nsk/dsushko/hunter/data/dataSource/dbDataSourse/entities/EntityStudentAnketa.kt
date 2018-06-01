package ru.nsk.dsushko.hunter.data.dataSource.dbDataSourse.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class EntityStudentAnketa (
        var school: String?,
        var faculty: String?,
        var speciality: String?,
        var graduateYear: String?,
        var interestingWorkfieldIds: List<Int>?,
        var interestingEventIds: List<Int>?,
        var comment: String?,
        var eventId: Int?,
        var city: String?,
        var isSubscribe: Boolean?,
        var name: String?,
        var phone: String?,
        var email: String?,
        var isAgree: Boolean?
)
{
    @PrimaryKey(autoGenerate = true)
    var id : Long? = null
}
