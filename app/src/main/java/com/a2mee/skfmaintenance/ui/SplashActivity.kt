package com.a2mee.skfmaintenance.ui

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.a2mee.skfmaintenance.R
import com.a2mee.skfmaintenance.db.SharedPrefrencesManager
import com.a2mee.skfmaintenance.util.Constants


class SplashActivity : AppCompatActivity() {

    private var userName: String? = null
    private var password: String? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val window: Window = getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this@SplashActivity, R.color.colorWhite))
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        userName = SharedPrefrencesManager(this@SplashActivity).getValue(Constants.USER_NAME)
        password = SharedPrefrencesManager(this@SplashActivity).getValue(Constants.PASSWORD)

        if (userName == null && password == null) {
            Handler().postDelayed(Runnable {
                val splashIntent = Intent(this, LoginActivity::class.java)
                startActivity(splashIntent)
                finish()
            }, 3000)
        } else {
            Handler().postDelayed(Runnable {
                val splashIntent = Intent(this, DashboardActivity::class.java)
                startActivity(splashIntent)
                finish()
            }, 3000)
        }
    }
}