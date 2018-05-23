package ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class DateAnswer : Serializable {
    @SerializedName("start")
    @Expose
    var start: String? = null
    @SerializedName("end")
    @Expose
    var end: String? = null
}
