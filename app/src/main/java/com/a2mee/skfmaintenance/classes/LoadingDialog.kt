package com.a2mee.skfmaintenance.classes

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.a2mee.skfmaintenance.R

class LoadingDialog {
   private lateinit var alertDialog: AlertDialog.Builder
    fun createDialog(context: Context, setCancellationOnTouch: Boolean): AlertDialog.Builder {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null, false)
         alertDialog = AlertDialog.Builder(context)

        alertDialog.setView(view)

        alertDialog.setCancelable(setCancellationOnTouch)
        return alertDialog

    }
    fun showDialog(){
        alertDialog.show()
    }
    fun dismissDialog(){

    }
}