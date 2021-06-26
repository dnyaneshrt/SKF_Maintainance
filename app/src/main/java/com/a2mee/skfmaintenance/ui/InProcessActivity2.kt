package com.a2mee.skfmaintenance.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.a2mee.skfmaintenance.R


class InProcessActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_process2)
        Toast.makeText(this,"2",Toast.LENGTH_LONG).show();
    }
}