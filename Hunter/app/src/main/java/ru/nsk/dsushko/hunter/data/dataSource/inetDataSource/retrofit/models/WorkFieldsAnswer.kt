package ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WorkFieldsAnswer {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("titleEng")
    @Expose
    var titleEng: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("hasVacancy")
    @Expose
    var hasVacancy: Boolean? = null
}