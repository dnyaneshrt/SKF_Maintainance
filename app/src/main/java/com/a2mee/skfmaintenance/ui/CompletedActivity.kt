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
import com.a2mee.skfmaintenance.adapter.CompletedAdapter
import com.a2mee.skfmaintenance.db.SharedPrefrencesManager
import com.a2mee.skfmaintenance.response.CompletedSOPByEnginnerResponse
import com.a2mee.skfmaintenance.retrofit.NetworkService
import com.a2mee.skfmaintenance.util.Constants
import kotlinx.coroutines.launch
import org.json.JSONException
import java.lang.NullPointerException

class CompletedActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "CompletedActivity"
    private var ivBackArrow: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var completedAdapter: CompletedAdapter? = null
    private var linearLayout: LinearLayoutManager? = null
    private var rvCompleted: RecyclerView? = null
    private var completedSOPByEnginnerList: List<CompletedSOPByEnginnerResponse>? = null
    private var employeeId: String? = null
    private var token: String? = null
    private var Authorization: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed)

        ivBackArrow = findViewById(R.id.iv_back_arrow)
        ivBackArrow!!.setOnClickListener(this)

        rvCompleted = findViewById(R.id.rv_completed)
        linearLayout = LinearLayoutManager(this)
        rvCompleted!!.setHasFixedSize(true)
        rvCompleted!!.layoutManager = linearLayout

        progressBar = findViewById(R.id.progressBar)

        employeeId = SharedPrefrencesManager(this@CompletedActivity).getValue(Constants.EMPLOYEE_ID)
        token = SharedPrefrencesManager(this@CompletedActivity).getValue(Constants.TOKEN)
        Authorization = "Bearer $token"

        lifecycleScope.launch {
            getCompletedSOPByEnginner()
        }
    }

    private suspend fun getCompletedSOPByEnginner() {

        progressBar!!.visibility = View.VISIBLE

        val response = NetworkService.invoke()?.getCompletedSOPByEnginner(employeeId!!, Authorization!!)
        try {
            Log.i(TAG, "onResponse ${response!!.body()}")

            if (response.isSuccessful) {
                Log.i(TAG, "onSuccess ${response!!.body()}")
                progressBar!!.visibility = View.GONE

                completedSOPByEnginnerList = response.body()

                completedAdapter = CompletedAdapter(this, completedSOPByEnginnerList)
                rvCompleted!!.adapter = completedAdapter
            } else {
                Toast.makeText(this@CompletedActivity, getString(R.string.data_not_coming), Toast.LENGTH_SHORT).show()
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