package com.a2mee.skfmaintenance.db

import android.content.Context
import android.content.SharedPreferences
import com.a2mee.skfmaintenance.util.Constants

class SharedPrefrencesManager(val context: Context) {

    fun setValue(key: String, value: String) {
        context.getSharedPreferences(Constants.SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE)?.edit()
            ?.putString(key, value)?.commit()
    }

    fun getValue(key: String): String? {
        return context.getSharedPreferences(Constants.SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE)
            .getString(key, null)
    }

    fun clearSharedPrefrences(){
        context?.getSharedPreferences(Constants.SHARED_PREFRENCE_NAME,Context.MODE_PRIVATE).edit().clear().apply()

    }
}