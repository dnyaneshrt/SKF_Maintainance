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
import com.a2mee.skfmaintenance.db.SharedPrefrencesManager
import com.a2mee.skfmaintenance.response.InProcessSOPByEnginnerResponse
import com.a2mee.skfmaintenance.retrofit.NetworkService
import com.a2mee.skfmaintenance.util.Constants
import kotlinx.coroutines.launch
import org.json.JSONException
import java.lang.NullPointerException

class InProcessActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "InProcessActivity"
    private var ivBackArrow: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var inProcessAdapter: InProcessAdapter? = null
    private var linearLayout: LinearLayoutManager? = null
    private var rvInProcess: RecyclerView? = null
    private var inProcessSOPByEnginnerList: List<InProcessSOPByEnginnerResponse>? = null
    private var employeeId: String? = null
    private var token: String? = null
    private var Authorization: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_process)

        ivBackArrow = findViewById(R.id.iv_back_arrow)
        ivBackArrow!!.setOnClickListener(this)

        rvInProcess = findViewById(R.id.rv_in_process)
        linearLayout = LinearLayoutManager(this)
        rvInProcess!!.setHasFixedSize(true)
        rvInProcess!!.layoutManager = linearLayout

        progressBar = findViewById(R.id.progressBar)

        employeeId = SharedPrefrencesManager(this@InProcessActivity).getValue(Constants.EMPLOYEE_ID)
        token = SharedPrefrencesManager(this@InProcessActivity).getValue(Constants.TOKEN)
        Authorization = "Bearer $token"

        lifecycleScope.launch {
            getInProcessSOPByEnginner()
        }
    }

    private suspend fun getInProcessSOPByEnginner() {

        progressBar!!.visibility = View.VISIBLE

        val response = NetworkService.invoke()?.getInProcessSOPByEnginner(employeeId!!, Authorization!!)
        try {
            Log.i(TAG, "onResponse ${response!!.body()}")

            if (response.isSuccessful) {
                Log.i(TAG, "onSuccess ${response!!.body()}")
                progressBar!!.visibility = View.GONE

                inProcessSOPByEnginnerList = response.body()

                inProcessAdapter = InProcessAdapter(this, inProcessSOPByEnginnerList)
                rvInProcess!!.adapter = inProcessAdapter
            } else {
                Toast.makeText(this@InProcessActivity, getString(R.string.data_not_coming), Toast.LENGTH_SHORT).show()
            }
        } catch (je: JSONException) {
            je.printStackTrace()
        } catch (npe: NullPointerException) {
            npe.printStackTrace()
        }
    }

    override fun onClick(v: View?) {

        var id = v?.id

        when (id) {

            R.id.iv_back_arrow ->
                onBackPressed()
        }
    }
}