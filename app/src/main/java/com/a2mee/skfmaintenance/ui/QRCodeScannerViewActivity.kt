package com.a2mee.skfmaintenance.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import me.dm7.barcodescanner.zxing.ZXingScannerView

class QRCodeScannerViewActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var zXingScannerView: ZXingScannerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_qrcode_scanner_view)

        zXingScannerView = ZXingScannerView(this@QRCodeScannerViewActivity)
        setContentView(zXingScannerView)

        Dexter.withContext(applicationContext)
            .withPermission(android.Manifest.permission.CAMERA)
            .withListener(object: PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    zXingScannerView!!.startCamera()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {

                }
            }).check()
    }

    override fun handleResult(rawResult: Result?) {
        val intent = Intent();
        intent.putExtra("qr_code_data", rawResult!!.text)
        setResult(RESULT_OK, intent);
        Log.i("Scan data", rawResult!!.text)
        Toast.makeText(this@QRCodeScannerViewActivity, "QR Code Scan Successfully", Toast.LENGTH_SHORT).show()
        onBackPressed()
        finish();
    }

    override fun onPause() {
        super.onPause()
        zXingScannerView!!.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        zXingScannerView!!.setResultHandler(this)
        zXingScannerView!!.startCamera()
    }
}