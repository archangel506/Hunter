package ru.nsk.dsushko.hunter.domain.models

data class StandartAnketa (
    var company: String?,
    var position: String?,
    var workfieldId: Int?,
    var likeReports: String?,
    var suggestions: String?,
    var technologiesId: List<Int>?,
    var otherTechnologies: String?,
    var interestingEventIds: List<Int>?,
    var isReadyToBeSpeaker: Boolean?,
    var eventId: Int?,
    var city: String?,
    var isSubscribe: Boolean?,
    var name: String?,
    var phone: String?,
    var email: String?,
    var isAgree: Boolean?
)