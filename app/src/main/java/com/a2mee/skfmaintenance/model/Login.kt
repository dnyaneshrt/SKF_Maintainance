package com.a2mee.skfmaintenance.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Login(

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("firstName")
    @Expose
    val firstName: String,

    @SerializedName("lastName")
    @Expose
    val lastName: String,

    @SerializedName("emailId")
    @Expose
    val emailId: String,

    @SerializedName("contact_no")
    @Expose
    val contactNo: String,

    @SerializedName("gender")
    @Expose
    val gender: String,

    @SerializedName("username")
    @Expose
    val username: String,

    @SerializedName("employee_id")
    @Expose
    val employeeId: String,

    @SerializedName("password")
    @Expose
    val password: String,

    @SerializedName("active")
    @Expose
    val active: String,

    @SerializedName("addedBy")
    @Expose
    val addedBy: String,

    @SerializedName("addedDateTime")
    @Expose
    val addedDateTime: String,

    @SerializedName("updateDateTime")
    @Expose
    val updateDateTime: String,
)