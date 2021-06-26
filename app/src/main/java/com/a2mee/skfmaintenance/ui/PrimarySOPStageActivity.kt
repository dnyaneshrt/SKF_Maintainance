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
import com.a2mee.skfmaintenance.adapter.PrimarySOPStageAdapter
import com.a2mee.skfmaintenance.db.SharedPrefrencesManager
import com.a2mee.skfmaintenance.response.PrimarySOPStagesBySOPResponse
import com.a2mee.skfmaintenance.retrofit.NetworkService
import com.a2mee.skfmaintenance.util.Constants
import kotlinx.coroutines.launch
import org.json.JSONException
import java.lang.NullPointerException

class PrimarySOPStageActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "PrimarySOPStageActivity"
    private var ivBackArrow: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var primarySOPStageAdapter: PrimarySOPStageAdapter? = null
    private var linearLayout: LinearLayoutManager? = null
    private var rvPrimarySOPStage: RecyclerView? = null
    private var primarySOPStagesBySOPList: List<PrimarySOPStagesBySOPResponse>? = null
    private var employeeId: String? = null
    private var token: String? = null
    private var Authorization: String? = null
    private var sopId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary_sopstage)

        ivBackArrow = findViewById(R.id.iv_back_arrow)
        ivBackArrow!!.setOnClickListener(this)

        rvPrimarySOPStage = findViewById(R.id.rv_primary_sop_stage)
        linearLayout = LinearLayoutManager(this)
        rvPrimarySOPStage!!.setHasFixedSize(true)
        rvPrimarySOPStage!!.layoutManager = linearLayout

        progressBar = findViewById(R.id.progressBar)

        sopId = intent.getIntExtra("sop_id",0)

        token = SharedPrefrencesManager(this@PrimarySOPStageActivity).getValue(Constants.TOKEN)
        Authorization = "Bearer $token"

        lifecycleScope.launch {
            getPrimarySOPStageBySOP()
        }
    }

    private suspend fun getPrimarySOPStageBySOP() {

        progressBar!!.visibility = View.VISIBLE

        val response = NetworkService.invoke()?.getPrimarySOPStagesBySOP(sopId!!, Authorization!!)

        try {
            if (response != null) {
                Log.i(TAG, "onResponse ${response!!.body()}")

                if (response.isSuccessful) {
                    Log.i(TAG, "onSuccess ${response!!.body()}")
                    progressBar!!.visibility = View.GONE

                    primarySOPStagesBySOPList = response.body()

                    primarySOPStageAdapter = PrimarySOPStageAdapter(this, primarySOPStagesBySOPList)
                    rvPrimarySOPStage!!.adapter = primarySOPStageAdapter
                } else {
                    Toast.makeText(this@PrimarySOPStageActivity, getString(R.string.data_not_coming), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@PrimarySOPStageActivity, getString(R.string.data_not_coming), Toast.LENGTH_SHORT).show()
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