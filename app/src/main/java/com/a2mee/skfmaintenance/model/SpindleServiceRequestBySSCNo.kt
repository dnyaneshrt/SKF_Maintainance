package com.a2mee.skfmaintenance.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SpindleServiceRequestBySSCNo (

    @SerializedName("spindleServicceRequestId")
    @Expose
    val spindleServicceRequestId: Int,

    @SerializedName("employeeId")
    @Expose
    val employeeId: Int,

    @SerializedName("sscNo")
    @Expose
    val sscNo: String,

    @SerializedName("spindleBrand")
    @Expose
    val spindleBrand: String,

    @SerializedName("machineBrand")
    @Expose
    val machineBrand: String,

    @SerializedName("serialNo")
    @Expose
    val serialNo: String,

    @SerializedName("spindleModel")
    @Expose
    val spindleModel: String,

    @SerializedName("machineModel")
    @Expose
    val machineModel: String,

    @SerializedName("spindleRequestedDate")
    @Expose
    val spindleRequestedDate: String,
)