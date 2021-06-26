package com.a2mee.skfmaintenance.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a2mee.skfmaintenance.R
import com.a2mee.skfmaintenance.adapter.InProcessAdapter
import com.a2mee.skfmaintenance.db.SharedPrefrencesManager
import com.a2mee.skfmaintenance.response.SpindleServiceRequestBySSCNoResponse
import com.a2mee.skfmaintenance.retrofit.NetworkService
import com.a2mee.skfmaintenance.util.Constants
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import org.json.JSONException
import java.util.*

class QRCodeScanActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "QRCodeScanActivity"
    private var ivBackArrow: ImageView? = null
    private var etQRCode: EditText? = null
    private var tvQRCodeGet: TextView? = null
    private var btnScanQRCode: Button? = null
        private var progressBar: ProgressBar?= null
        private var token: String? = null
        private var Authorization: String? = null
    private var qrCodeScanAdapter: InProcessAdapter? = null
    private var linearLayout: LinearLayoutManager? = null
    private var rvQRScanDetails: RecyclerView? = null
    private var qrCodeDetailsList: SpindleServiceRequestBySSCNoResponse? = null

    private var tvSpindleServicceRequestId: TextView? = null
    private var tvEmployeeId: TextView? = null
    private var tvSSCNo: TextView? = null
    private var tvSpindleBrand: TextView? = null
    private var tvMachineBrand: TextView? = null
    private var tvSerialNo: TextView? = null
    private var tvSpindleModel: TextView? = null
    private var tvMachineModel: TextView? = null
    private var tvSpindleRequestedDate: TextView? = null
    private var cvUserDetails: CardView? = null
    private val QR_CODE_REQUEST = 1
    private var btnSendUserDetails: Button? = null
    private var employeeId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_scan)

        ivBackArrow = findViewById(R.id.iv_back_arrow)
        ivBackArrow!!.setOnClickListener(this)

        etQRCode = findViewById(R.id.et_qr_code)
        tvQRCodeGet = findViewById(R.id.tv_get_qr_code)
        tvQRCodeGet!!.setOnClickListener(this)
        btnScanQRCode = findViewById(R.id.btn_scan_qr_code)
        btnScanQRCode!!.setOnClickListener(this)

        progressBar = findViewById(R.id.progressBar)

        /*
        rvQRScanDetails = findViewById(R.id.rv_qr_code_details)
        linearLayout = LinearLayoutManager(this)
        rvQRScanDetails!!.setHasFixedSize(true)
        rvQRScanDetails!!.layoutManager = linearLayout

        qrCodeScanAdapter = QRCodeScanAdapter(this, qrCodeDetailsList)
        rvQRScanDetails!!.adapter = qrCodeScanAdapter
        */

        // tvSpindleServicceRequestId = findViewById(R.id.tv_spindleServicceRequestId)
        // tvEmployeeId = findViewById(R.id.tv_employeeId)
        tvSSCNo = findViewById(R.id.tv_sscNo)
        tvSpindleBrand = findViewById(R.id.tv_spindleBrand)
        tvMachineBrand = findViewById(R.id.tv_machineBrand)
        tvSerialNo = findViewById(R.id.tv_serialNo)
        tvSpindleModel = findViewById(R.id.tv_spindleModel)
        tvMachineModel = findViewById(R.id.tv_machineModel)
        tvSpindleRequestedDate = findViewById(R.id.tv_spindleRequestedDate)

        cvUserDetails = findViewById(R.id.cv_user_details)
        btnSendUserDetails = findViewById(R.id.btn_send_user_details)
        btnSendUserDetails!!.setOnClickListener(this)

        token = SharedPrefrencesManager(this@QRCodeScanActivity).getValue(Constants.TOKEN)
        Authorization = "Bearer $token"

        employeeId = SharedPrefrencesManager(this@QRCodeScanActivity).getValue(Constants.EMPLOYEE_ID)
    }

    override fun onClick(v: View?) {

        val id = v?.id
        when (id) {

            R.id.iv_back_arrow ->
                onBackPressed()

            R.id.tv_get_qr_code ->
                lifecycleScope.launch {
                    getQRCodeData(etQRCode!!.text.toString())
                }

            R.id.btn_scan_qr_code ->
                startActivityForResult(Intent(this@QRCodeScanActivity, QRCodeScannerViewActivity::class.java), QR_CODE_REQUEST)

            R.id.btn_send_user_details ->
                lifecycleScope.launch {
                    sendUserDetails()
                }
        }
    }

    private suspend fun getQRCodeData(strQRCode: String?) {

        // val strQRCode: String = etQRCode!!.text.toString()

        if (strQRCode!!.isEmpty() && strQRCode.length == 0) {
            etQRCode!!.text.toString()
        }

        if (strQRCode.length == 0 && strQRCode.isEmpty() && strQRCode.equals("")) {
             Toast.makeText(this@QRCodeScanActivity, getString(R.string.please_enter_qr_code), Toast.LENGTH_SHORT).show()
        } else {

            progressBar!!.visibility = View.VISIBLE

            // Using Coroutine
            val response = NetworkService.invoke()?.getSpindleServiceRequestBySSCNo(strQRCode, Authorization!!)
            try {
                Log.i(TAG, "onRespose: ${response!!.body()}")
                if (response!!.isSuccessful) {

                    Log.i(TAG, "onSuccess: ${response.body()}")

                    progressBar!!.visibility = View.GONE
                    cvUserDetails!!.visibility = View.VISIBLE
                    btnSendUserDetails!!.visibility = View.VISIBLE

                    val spindleServiceRequestBySSCNoList = response!!.body()?.SpindleServiceRequestBySSCNoList

                    // tvSpindleServicceRequestId!!.text = " ${spindleServiceRequestBySSCNoList!!.spindleServicceRequestId}"
                    // tvEmployeeId!!.text = " ${spindleServiceRequestBySSCNoList!!.employeeId}"
                    tvSSCNo!!.text = spindleServiceRequestBySSCNoList!!.sscNo
                    tvSpindleBrand!!.text = spindleServiceRequestBySSCNoList!!.spindleBrand
                    tvMachineBrand!!.text = spindleServiceRequestBySSCNoList!!.machineBrand
                    tvSerialNo!!.text = spindleServiceRequestBySSCNoList!!.serialNo
                    tvSpindleModel!!.text = spindleServiceRequestBySSCNoList!!.spindleModel
                    tvMachineModel!!.text = spindleServiceRequestBySSCNoList!!.machineModel
                    tvSpindleRequestedDate!!.text = spindleServiceRequestBySSCNoList!!.spindleRequestedDate
                } else {
                    Toast.makeText(this@QRCodeScanActivity, getString(R.string.data_not_coming), Toast.LENGTH_SHORT).show()
                }
            } catch (je: JSONException) {
                je.printStackTrace()
            } catch (npe: NullPointerException) {
                npe.printStackTrace()
            }

            /*
            val response = NetworkService.invoke()?.getSpindleServiceRequestBySSCNo(strQRCode, Authorization!!)
            response!!.enqueue(object: Callback<SpindleServiceRequestBySSCNoResponse>{
                override fun onResponse(
                    call: Call<SpindleServiceRequestBySSCNoResponse>,
                    response: Response<SpindleServiceRequestBySSCNoResponse>
                ) {
                    Log.i(TAG, "onRespose: ${response.body()}")

                    if (response.isSuccessful) {
                        try {
                            Log.i(TAG, "onSuccess: ${response.body()}")

                            progressBar!!.visibility = View.GONE

                            qrCodeDetailsList = response.body()

                        } catch (je: JSONException) {
                            je.printStackTrace()
                        }
                    } else {
                        Toast.makeText(this@QRCodeScanActivity, getString(R.string.data_not_coming), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(
                    call: Call<SpindleServiceRequestBySSCNoResponse>,
                    t: Throwable
                ) {
                    Log.e(TAG, "onError: ${t.message.toString()}")

                    progressBar!!.visibility = View.GONE
                }
            })
            */

        }
    }

    private suspend fun sendUserDetails() {

        progressBar!!.visibility = View.VISIBLE

        /*
        val params = HashMap<String, String>()
        params["employeeId"] = employeeId!!
        params["sscNo"] = tvSSCNo!!.text.toString()
        params["spindleBrand"] = tvSpindleBrand!!.text.toString()
        params["machineBrand"] = tvMachineBrand!!.text.toString()
        params["serialNo"] = tvSerialNo!!.text.toString()
        params["spindleModel"] = tvSpindleModel!!.text.toString()
        params["machineModel"] = tvMachineModel!!.text.toString()
        params["spindleRequestedDate"] = tvSpindleRequestedDate!!.text.toString()
        */

        val jsonObject = JsonObject()
        jsonObject.addProperty("employeeId", employeeId)
        jsonObject.addProperty("sscNo", tvSSCNo!!.text.toString())
        jsonObject.addProperty("spindleBrand", tvSpindleBrand!!.text.toString())
        jsonObject.addProperty("machineBrand", tvMachineBrand!!.text.toString())
        jsonObject.addProperty("serialNo", tvSerialNo!!.text.toString())
        jsonObject.addProperty("spindleModel", tvSpindleModel!!.text.toString())
        jsonObject.addProperty("machineModel", tvMachineModel!!.text.toString())
        jsonObject.addProperty("spindleRequestedDate", tvSpindleRequestedDate!!.text.toString())

        val response = NetworkService.invoke()?.addSpindleServiceRequestByEngineer(jsonObject, Authorization!!)
        try {
            Log.i(TAG, "onRespose: ${response!!.body()}")
            progressBar!!.visibility = View.GONE

            if (response.isSuccessful) {
                Log.i(TAG, "onSuccess: ${response!!.body()}")

                val message = response!!.message()
                Toast.makeText(this@QRCodeScanActivity, message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@QRCodeScanActivity, getString(R.string.data_not_coming), Toast.LENGTH_SHORT).show()
            }
        } catch (je: JSONException) {
            je.printStackTrace()
        } catch (npe: java.lang.NullPointerException) {
            npe.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val strQRCode = data!!.getStringExtra("qr_code_data")

                lifecycleScope.launch {
                    getQRCodeData(strQRCode)
                }
            }
        }
    }
}