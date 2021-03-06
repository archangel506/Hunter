package ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class CityAnswer : Serializable{
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("nameRus")
    @Expose
    var nameRus: String? = null
    @SerializedName("nameEng")
    @Expose
    var nameEng: String? = null
    @SerializedName("icon")
    @Expose
    var icon: String? = null
    @SerializedName("isActive")
    @Expose
    var isActive: Boolean? = null
}
