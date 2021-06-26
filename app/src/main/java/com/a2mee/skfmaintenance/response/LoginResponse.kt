package com.a2mee.skfmaintenance.response

import com.a2mee.skfmaintenance.model.Login
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("code")
    @Expose
    val code: Int,

    @SerializedName("message")
    @Expose
    val message: String,

    @SerializedName("token")
    @Expose
    val token: String,

    @SerializedName("data")
    @Expose
    val loginList: Login
)