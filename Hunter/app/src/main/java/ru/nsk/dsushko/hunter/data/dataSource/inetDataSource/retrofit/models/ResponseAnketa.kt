package ru.nsk.dsushko.hunter.data.dataSource.inetDataSource.retrofit.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseAnketa {
    @SerializedName("result")
    @Expose
    var result: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
}