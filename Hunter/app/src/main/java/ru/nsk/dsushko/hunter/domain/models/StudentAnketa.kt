package ru.nsk.dsushko.hunter.domain.models

data class StudentAnketa (
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