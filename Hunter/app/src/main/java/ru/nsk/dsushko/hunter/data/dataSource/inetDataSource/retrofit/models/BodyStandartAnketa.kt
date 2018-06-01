package ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




class BodyStandartAnketa
(
        @SerializedName("company")
        @Expose
        var company: String?,
        @SerializedName("position")
        @Expose
        var position: String?,
        @SerializedName("workfieldId")
        @Expose
        var workfieldId: Int?,
        @SerializedName("likeReports")
        @Expose
        var likeReports: String?,
        @SerializedName("suggestions")
        @Expose
        var suggestions: String?,
        @SerializedName("technologiesId")
        @Expose
        var technologiesId: List<Int>?,
        @SerializedName("otherTechnologies")
        @Expose
        var otherTechnologies: String?,
        @SerializedName("interestingEventIds")
        @Expose
        var interestingEventIds: List<Int>?,
        @SerializedName("isReadyToBeSpeaker")
        @Expose
        var isReadyToBeSpeaker: Boolean?,
        @SerializedName("eventId")
        @Expose
        var eventId: Int?,
        @SerializedName("city")
        @Expose
        var city: String?,
        @SerializedName("isSubscribe")
        @Expose
        var isSubscribe: Boolean?,
        @SerializedName("name")
        @Expose
        var name: String?,
        @SerializedName("phone")
        @Expose
        var phone: String?,
        @SerializedName("email")
        @Expose
        var email: String?,
        @SerializedName("isAgree")
        @Expose
        var isAgree: Boolean?
)
