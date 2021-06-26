package com.a2mee.skfmaintenance.response

import com.a2mee.skfmaintenance.model.SpindleServiceRequestBySSCNo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SpindleServiceRequestBySSCNoResponse (

    @SerializedName("code")
    @Expose
    val code: Int,

    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("data")
    @Expose
    val SpindleServiceRequestBySSCNoList: SpindleServiceRequestBySSCNo,
)