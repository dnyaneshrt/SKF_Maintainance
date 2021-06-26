package com.a2mee.skfmaintenance.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a2mee.skfmaintenance.R
import com.a2mee.skfmaintenance.adapter.InProcessAdapter
import com.a2mee.skfmaintenance.adapter.PendingAdapter
import com.a2mee.skfmaintenance.db.SharedPrefrencesManager
import com.a2mee.skfmaintenance.response.InProcessSOPByEnginnerResponse
import com.a2mee.skfmaintenance.response.PendingSOPByEnginnerResponse
import com.a2mee.skfmaintenance.retrofit.NetworkService
import com.a2mee.skfmaintenance.util.Constants
import kotlinx.coroutines.launch
import org.json.JSONException
import java.lang.NullPointerException

class PendingActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "PendingActivity"
    private var ivBackArrow: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var pendingAdapter: PendingAdapter? = null
    private var linearLayout: LinearLayoutManager? = null
    private var rvPending: RecyclerView? = null
    private var pendingSOPByEnginnerList: List<PendingSOPByEnginnerResponse>? = null
    private var employeeId: String? = null
    private var token: String? = null
    private var Authorization: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending)

        ivBackArrow = findViewById(R.id.iv_back_arrow)
        ivBackArrow!!.setOnClickListener(this)

        rvPending = findViewById(R.id.rv_pending)
        linearLayout = LinearLayoutManager(this)
        rvPending!!.setHasFixedSize(true)
        rvPending!!.layoutManager = linearLayout

        progressBar = findViewById(R.id.progressBar)

        employeeId = SharedPrefrencesManager(this@PendingActivity).getValue(Constants.EMPLOYEE_ID)
        token = SharedPrefrencesManager(this@PendingActivity).getValue(Constants.TOKEN)
        Authorization = "Bearer $token"

        lifecycleScope.launch {
            getPendingSOPByEnginner()
        }
    }

    private suspend fun getPendingSOPByEnginner() {

        progressBar!!.visibility = View.VISIBLE

        val response = NetworkService.invoke()?.getPendingSOPByEnginner(employeeId!!, Authorization!!)
        try {
            Log.i(TAG, "onResponse ${response!!.body()}")

            if (response.isSuccessful) {
                Log.i(TAG, "onSuccess ${response!!.body()}")
                progressBar!!.visibility = View.GONE

                pendingSOPByEnginnerList = response.body()

                pendingAdapter = PendingAdapter(this, pendingSOPByEnginnerList)
                rvPending!!.adapter = pendingAdapter
            } else {
                Toast.makeText(this@PendingActivity, getString(R.string.data_not_coming), Toast.LENGTH_SHORT).show()
            }
        } catch (je: JSONException) {
            je.printStackTrace()
        } catch (npe: NullPointerException) {
            npe.printStackTrace()
        }
    }

    override fun onClick(v: View?) {

        val id = v?.id

        when (id) {

            R.id.iv_back_arrow ->
                onBackPressed()
        }
    }
}