package com.a2mee.skfmaintenance.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PrimarySOPStagesBySOPResponse (

    @SerializedName("sopStageId")
    @Expose
    val sopStageId: Int,

    @SerializedName("sop")
    @Expose
    val sop: Int,

    @SerializedName("sopId")
    @Expose
    val sopId: Int,

    @SerializedName("stageNo")
    @Expose
    val stageNo: Int,

    @SerializedName("stageName")
    @Expose
    val stageName: String,

    @SerializedName("stageType")
    @Expose
    val stageType: String,

    @SerializedName("status")
    @Expose
    val status: String,

    @SerializedName("startDate")
    @Expose
    val startDate: String,

    @SerializedName("completeDate")
    @Expose
    val completeDate: String,

    @SerializedName("added_date")
    @Expose
    val addedDate: String,
)