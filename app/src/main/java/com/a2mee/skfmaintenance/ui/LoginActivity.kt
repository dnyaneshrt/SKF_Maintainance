package com.a2mee.skfmaintenance.ui

import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.a2mee.skfmaintenance.R
import com.a2mee.skfmaintenance.classes.CheckInternetConnection
import com.a2mee.skfmaintenance.classes.LoadingDialog
import com.a2mee.skfmaintenance.classes.Toaster
import com.a2mee.skfmaintenance.db.SharedPrefrencesManager
import com.a2mee.skfmaintenance.response.LoginResponse
import com.a2mee.skfmaintenance.retrofit.NetworkService
import com.a2mee.skfmaintenance.util.Constants
import com.google.gson.JsonObject
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"

    private var etUserName: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null

    private var token: String? = null
    private var id: String? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var contactNo: String? = null
    private var gender: String? = null
    private var userName: String? = null
    private var employeeId: String? = null
    private var password: String? = null
    private var active: String? = null
    private var addedBy: String? = null
    private var addedDateTime: String? = null
    private var updateDateTime: String? = null

    private var dialogConnection: Dialog? = null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val window: Window = getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this@LoginActivity, R.color.colorDarkBlue))

        etUserName = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.buttonLogin)

        btnLogin!!.setOnClickListener({

            val strUserName = etUserName!!.text.toString()
            val strPassword = etPassword!!.text.toString()

            if (strUserName.length <= 0 && strUserName.isEmpty() && strUserName.equals("")) {
                Toast.makeText(
                    this,
                    getString(R.string.please_enter_the_user_name),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (strPassword.length <= 0 && strPassword.isEmpty() && strPassword.equals("")) {
                Toast.makeText(
                    this,
                    getString(R.string.please_enter_the_password),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                doLogin(strUserName, strPassword)
            }
        })
    }

    private fun doLogin(strUserName: String, strPassword: String) {

        val progressAlert = LoadingDialog().createDialog(this, false).create()
        progressAlert.show()

        val jsonObject = JsonObject()
        jsonObject.addProperty("userId", strUserName)
        jsonObject.addProperty("password", strPassword)
        val response = NetworkService.invoke()?.doLogin(jsonObject)

        response?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                progressAlert.dismiss()

                Log.d(TAG, "onResponse: ${response.body()}")
                val loginResponse = response.body();

                if (response.isSuccessful) {

                    if (response.body()!!.code == 200) {
                        try {

                            token = loginResponse!!.token
                            id = loginResponse.loginList.id
                            firstName = loginResponse.loginList.firstName
                            lastName = loginResponse.loginList.lastName
                            email = loginResponse.loginList.emailId
                            contactNo = loginResponse.loginList.contactNo
                            gender = loginResponse.loginList.gender
                            userName = loginResponse.loginList.username
                            employeeId = loginResponse.loginList.employeeId
                            password = loginResponse.loginList.password
                            active = loginResponse.loginList.active
                            addedBy = loginResponse.loginList.addedBy
                            addedDateTime = loginResponse.loginList.addedDateTime
                            updateDateTime = loginResponse.loginList.updateDateTime

                            SharedPrefrencesManager(this@LoginActivity).setValue(
                                Constants.TOKEN,
                                token!!
                            )

                            SharedPrefrencesManager(this@LoginActivity).setValue(
                                Constants.ID,
                                id!!
                            )

                            SharedPrefrencesManager(this@LoginActivity).setValue(
                                Constants.FIRST_NAME,
                                firstName!!
                            )

                            SharedPrefrencesManager(this@LoginActivity).setValue(
                                Constants.LAST_NAME,
                                lastName!!
                            )

                            SharedPrefrencesManager(this@LoginActivity).setValue(
                                Constants.EMAIL_ID,
                                email!!
                            )

                            SharedPrefrencesManager(this@LoginActivity).setValue(
                                Constants.CONTACT_NO,
                                contactNo!!
                            )

                            SharedPrefrencesManager(this@LoginActivity).setValue(
                                Constants.GENDER,
                                gender!!
                            )

                            SharedPrefrencesManager(this@LoginActivity).setValue(
                                Constants.USER_NAME,
                                userName!!
                            )

                            SharedPrefrencesManager(this@LoginActivity).setValue(
                                Constants.EMPLOYEE_ID,
                                employeeId!!
                            )

                            SharedPrefrencesManager(this@LoginActivity).setValue(
                                Constants.PASSWORD,
                                password!!
                            )

                            SharedPrefrencesManager(this@LoginActivity).setValue(
                                Constants.ACTIVE,
                                active!!
                            )

                            if (addedBy == null) {
                                SharedPrefrencesManager(this@LoginActivity).setValue(
                                    Constants.ADDED_BY,
                                    ""
                                )
                            } else {
                                SharedPrefrencesManager(this@LoginActivity).setValue(
                                    Constants.ADDED_BY,
                                    addedBy!!
                                )
                            }

                            if (addedDateTime == null) {
                                SharedPrefrencesManager(this@LoginActivity).setValue(
                                    Constants.ADDED_DATE_TIME,
                                    ""
                                )
                            } else {
                                SharedPrefrencesManager(this@LoginActivity).setValue(
                                    Constants.ADDED_DATE_TIME,
                                    addedDateTime!!
                                )
                            }

                            if (updateDateTime == null) {
                                SharedPrefrencesManager(this@LoginActivity).setValue(
                                    Constants.UPDATE_DATE_TIME,
                                    "l"
                                )
                            } else {
                                SharedPrefrencesManager(this@LoginActivity).setValue(
                                    Constants.UPDATE_DATE_TIME,
                                    updateDateTime!!
                                )
                            }

                            // Toaster.showGreen(this@LoginActivity, loginResponse!!.message)
                            Toast.makeText(this@LoginActivity, loginResponse!!.message, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                            finish()
                        } catch (je: JSONException) {
                            je.printStackTrace()
                        } catch (npe: NullPointerException) {
                            npe.printStackTrace()
                        }
                    } else {
                        Toaster.showRed(this@LoginActivity, loginResponse!!.message)
                    }
                } else {
                    Toaster.show(this@LoginActivity, getString(R.string.user_is_not_logged_in))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                progressAlert.dismiss()
                Log.e("onError", t.message.toString())
                progressAlert.dismiss()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        dialogConnection = Dialog(this@LoginActivity)
        dialogConnection!!.setContentView(R.layout.dialog_no_connection)
        val buttonRetry: Button = dialogConnection!!.findViewById(R.id.buttonRetry)
        buttonRetry.setOnClickListener {
            if (CheckInternetConnection.checkConnection(this)) {
                dialogConnection!!.dismiss()
            } else {
                dialogConnection!!.show()
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            if (CheckInternetConnection.checkConnection(this)) {
                dialogConnection!!.dismiss()
            } else {
                dialogConnection!!.show()
            }
        }
        super.onWindowFocusChanged(hasFocus)
    }
}