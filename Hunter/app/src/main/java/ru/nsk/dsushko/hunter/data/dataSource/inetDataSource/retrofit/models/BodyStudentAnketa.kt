package ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




class BodyStudentAnketa
(
        @SerializedName("school")
        @Expose
        var school: String?,
        @SerializedName("faculty")
        @Expose
        var faculty: String?,
        @SerializedName("speciality")
        @Expose
        var speciality: String?,
        @SerializedName("graduateYear")
        @Expose
        var graduateYear: String?,
        @SerializedName("interestingWorkfieldIds")
        @Expose
        var interestingWorkfieldIds: List<Int>?,
        @SerializedName("interestingEventIds")
        @Expose
        var interestingEventIds: List<Int>?,
        @SerializedName("comment")
        @Expose
        var comment: String?,
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