package com.a2mee.skfmaintenance.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployeeDashboardResponse (

    @SerializedName("inprocessSop")
    @Expose
    val inprocessSop: String,

    @SerializedName("completedSop")
    @Expose
    val completedSop: String,

    @SerializedName("pendingSop")
    @Expose
    val pendingSop: String
)