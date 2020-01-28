package com.example.firebaseretrofit.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MarkPointRequest {
    @SerializedName("marcacao")
    lateinit var mark: Marking

    companion object {
        var marking: Marking = Marking()
    }
    data class Response(
        val name: String?
    )
}

class Marking {
    @SerializedName("id")
    var id: String? = "1"
    @SerializedName("name")
    var name: String? = "Aldemir Goems"
    @SerializedName("date")
    var date: String? = "26/01/2020"
    @SerializedName("hour")
    var hour: String? = "12:00:20"
}

class MarkPointResponse(@SerializedName("MarcarPontoResult") var markPointResult: MarkPointResult) :
    Serializable

data class MarkPointResult(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("hour") var hour: String? = null
)