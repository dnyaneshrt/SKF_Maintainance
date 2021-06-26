package com.a2mee.skfmaintenance.classes

import android.content.Context
import android.graphics.PorterDuff
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.a2mee.skfmaintenance.R


/**
 * Created by Nilesh on 23,February,2021
 */
object Toaster {
    fun show(context: Context, text: CharSequence) {
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.view!!.background.setColorFilter(
            ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN
        )
        val textView = toast!!.view!!.findViewById(android.R.id.message) as TextView
        textView.setTextColor(ContextCompat.getColor(context, R.color.black))
        toast.show()
    }

    fun showGreen(context: Context, text: CharSequence) {
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.view!!.background.setColorFilter(
            ContextCompat.getColor(context, R.color.colorGreen), PorterDuff.Mode.SRC_IN
        )
        val textView = toast!!.view!!.findViewById(android.R.id.message) as TextView
        textView.setTextColor(ContextCompat.getColor(context, R.color.white))
        toast.show()
    }
    fun showRed(context: Context, text: CharSequence) {
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.view!!.background.setColorFilter(
            ContextCompat.getColor(context, R.color.colorRed), PorterDuff.Mode.SRC_IN
        )
        val textView = toast!!.view!!.findViewById(android.R.id.message) as TextView
        textView.setTextColor(ContextCompat.getColor(context, R.color.white))
        toast.show()
    }
}