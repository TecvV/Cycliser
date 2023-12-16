package com.example.app.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import com.example.app.R

class LoadingDialog(val mActivity: Activity) {
    private lateinit var isDialog: AlertDialog
    fun startLoading(){
        /** Setting view */
        val inflater = mActivity.layoutInflater
        val dialogview = inflater.inflate(R.layout.loading_item,null)
        /**Set Dialog*/
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogview)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun isDismiss(){
        isDialog.dismiss()
    }
}