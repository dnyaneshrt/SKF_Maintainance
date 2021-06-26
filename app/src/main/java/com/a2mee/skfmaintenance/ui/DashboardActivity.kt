package com.a2mee.skfmaintenance.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.a2mee.skfmaintenance.R
import com.a2mee.skfmaintenance.classes.CheckInternetConnection
import com.a2mee.skfmaintenance.db.SharedPrefrencesManager
import com.a2mee.skfmaintenance.response.EmployeeDashboardResponse
import com.a2mee.skfmaintenance.retrofit.NetworkService
import com.a2mee.skfmaintenance.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "DashboardActivity"

    private var tvInProcessCount: TextView? = null
    private var tvCompleteCount: TextView? = null
    private var tvPendingCount: TextView? = null

    private var tvInProcessCount2: TextView? = null
    private var tvCompleteCount2: TextView? = null
    private var tvPendingCount2: TextView? = null

    private var dialogConnection: Dialog? = null
    private var employeeId: String? = null
    private var token: String? = null
    private var Authorization: String? = null

    private var pbInProcessCount: ProgressBar? = null
    private var pbCompleteCount: ProgressBar? = null
    private var pbPendingCount: ProgressBar? = null

    private var pbInProcessCount2: ProgressBar? = null
    private var pbCompleteCount2: ProgressBar? = null
    private var pbPendingCount2: ProgressBar? = null

    private var ivLogout: ImageView? = null
    private var ivQRScan: ImageView? = null

    private var cvInProcess: CardView? = null
    private var cvPending: CardView? = null
    private var cvCompleted: CardView? = null

    private var cvInProcess2: CardView? = null
    private var cvPending2: CardView? = null
    private var cvCompleted2: CardView? = null
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val window: Window = getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this@DashboardActivity, R.color.colorWhite))
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        tvInProcessCount = findViewById(R.id.tv_in_process_count)
        tvCompleteCount = findViewById(R.id.tv_complete_count)
        tvPendingCount = findViewById(R.id.tv_pending_count)


        tvInProcessCount2 = findViewById(R.id.tv_in_process_count_2)
        tvCompleteCount2 = findViewById(R.id.tv_complete_count_2)
        tvPendingCount2 = findViewById(R.id.tv_pending_count_2)

        pbInProcessCount = findViewById(R.id.progressBar_in_process_count)
        pbCompleteCount = findViewById(R.id.progressBar_complete_count)
        pbPendingCount = findViewById(R.id.progressBar_pending_count)

        pbInProcessCount2 = findViewById(R.id.progressBar_in_process_count_2)
        pbCompleteCount2 = findViewById(R.id.progressBar_complete_count_2)
        pbPendingCount2 = findViewById(R.id.progressBar_pending_count_2)

        ivLogout = findViewById(R.id.iv_logout)
        ivLogout!!.setOnClickListener(this)
        ivQRScan = findViewById(R.id.iv_qr_code_scan)
        ivQRScan!!.setOnClickListener(this)

        cvInProcess = findViewById(R.id.cv_in_process)
        cvInProcess!!.setOnClickListener(this)

        cvPending = findViewById(R.id.cv_pending)
        cvPending!!.setOnClickListener(this)

        cvCompleted = findViewById(R.id.cv_completed)
        cvCompleted!!.setOnClickListener(this)

        cvInProcess2 = findViewById(R.id.cv_in_process_2)
        cvInProcess2!!.setOnClickListener(this)

        cvPending2 = findViewById(R.id.cv_pending_2)
        cvPending2!!.setOnClickListener(this)

        cvCompleted2 = findViewById(R.id.cv_completed_2)
        cvCompleted2!!.setOnClickListener(this)

        employeeId = SharedPrefrencesManager(this@DashboardActivity).getValue(Constants.EMPLOYEE_ID)
        token = SharedPrefrencesManager(this@DashboardActivity).getValue(Constants.TOKEN)

        Authorization = "Bearer $token"

        getEmployeeWorkCount()
    }

    private fun getEmployeeWorkCount() {

        pbInProcessCount!!.visibility = View.VISIBLE
        pbCompleteCount!!.visibility = View.VISIBLE
        pbPendingCount!!.visibility = View.VISIBLE
        val response =
            NetworkService.invoke()?.getEngineerMobileDashboard(employeeId!!, Authorization!!)
        response?.enqueue(object : Callback<EmployeeDashboardResponse> {
            override fun onResponse(
                call: Call<EmployeeDashboardResponse>,
                response: Response<EmployeeDashboardResponse>
            ) {
                Log.i(TAG, "onRespose: ${response.body()}")

                pbInProcessCount!!.visibility = View.GONE
                pbCompleteCount!!.visibility = View.GONE
                pbPendingCount!!.visibility = View.GONE

                tvInProcessCount!!.visibility = View.VISIBLE
                tvCompleteCount!!.visibility = View.VISIBLE
                tvPendingCount!!.visibility = View.VISIBLE

                pbInProcessCount2!!.visibility = View.GONE
                pbCompleteCount2!!.visibility = View.GONE
                pbPendingCount2!!.visibility = View.GONE

                tvInProcessCount2!!.visibility = View.VISIBLE
                tvCompleteCount2!!.visibility = View.VISIBLE
                tvPendingCount2!!.visibility = View.VISIBLE

                val employeeDashboardResponse = response.body()

                if (response.isSuccessful) {

                    tvInProcessCount!!.text = employeeDashboardResponse!!.inprocessSop
                    tvCompleteCount!!.text = employeeDashboardResponse!!.completedSop
                    tvPendingCount!!.text = employeeDashboardResponse!!.pendingSop
                } else {
                    Toast.makeText(
                        this@DashboardActivity,
                        "Count data not coming",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<EmployeeDashboardResponse>, t: Throwable) {
                Log.e(TAG, "onError: ${t.message.toString()}")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        dialogConnection = Dialog(this@DashboardActivity)
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

    override fun onClick(v: View?) {

        val id = v?.id

        when (id) {

            R.id.iv_logout ->
                logoutUserDialog()

            R.id.iv_qr_code_scan ->
                startActivity(Intent(this@DashboardActivity, QRCodeScanActivity::class.java))

            R.id.cv_in_process ->
                startActivity(Intent(this@DashboardActivity, InProcessActivity::class.java))

            R.id.cv_pending ->
                startActivity(Intent(this@DashboardActivity, PendingActivity::class.java))

            R.id.cv_completed ->
                startActivity(Intent(this@DashboardActivity, CompletedActivity::class.java))

            R.id.cv_in_process_2->
                startActivity(Intent(this@DashboardActivity, InProcessActivity2::class.java))

            R.id.cv_pending_2 ->
                startActivity(Intent(this@DashboardActivity, PendingActivity2::class.java))

            R.id.cv_completed_2 ->
                startActivity(Intent(this@DashboardActivity, CompletedActivity2::class.java))

        }
    }

    private fun logoutUserDialog() {

        val alertDialog = AlertDialog.Builder(this@DashboardActivity)
            .setCancelable(true)
            .setIcon(R.drawable.img_app_icon)
            .setTitle("Logout")
            .setMessage("Do you want to logout")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                logoutUser()
            })

            .setNegativeButton(
                "No",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
        val alert = alertDialog.create()
        alert.show()
    }

    private fun logoutUser() {
        SharedPrefrencesManager(this@DashboardActivity).clearSharedPrefrences()
        Toast.makeText(
            this@DashboardActivity,
            getString(R.string.logout_successfully),
            Toast.LENGTH_SHORT
        ).show()
        startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
        finish()
    }
}