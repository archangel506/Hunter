package ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class EventAnswer : Serializable {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("cities")
    @Expose
    var cities: List<CityAnswer>? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("format")
    @Expose
    var format: Int? = null
    @SerializedName("date")
    @Expose
    var date: DateAnswer? = null
    @SerializedName("cardImage")
    @Expose
    var cardImage: String? = null
    @SerializedName("status")
    @Expose
    var status: Int? = null
    @SerializedName("iconStatus")
    @Expose
    var iconStatus: String? = null
    @SerializedName("eventFormat")
    @Expose
    var eventFormat: String? = null
    @SerializedName("eventFormatEng")
    @Expose
    var eventFormatEng: String? = null
}
