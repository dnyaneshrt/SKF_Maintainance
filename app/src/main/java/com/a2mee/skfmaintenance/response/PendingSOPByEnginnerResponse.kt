package com.a2mee.skfmaintenance.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PendingSOPByEnginnerResponse (

    @SerializedName("sprindleServiceRequestId")
    @Expose
    val sprindleServiceRequestId: Int,

    @SerializedName("sopId")
    @Expose
    val sopId: Int,

    @SerializedName("customerCode")
    @Expose
    val customerCode: String,

    @SerializedName("customerName")
    @Expose
    val customerName: String,

    @SerializedName("spindleBrand")
    @Expose
    val spindleBrand: String,

    @SerializedName("spindleModel")
    @Expose
    val spindleModel: String,

    @SerializedName("issue")
    @Expose
    val issue: String,

    @SerializedName("noOfstage")
    @Expose
    val noOfstage: String,

    @SerializedName("noOfStageComplete")
    @Expose
    val noOfStageComplete: String
)