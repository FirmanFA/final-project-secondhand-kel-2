package com.binar.secondhand.kel2.utils

import android.content.Context

private var loadingDialog: LoadingDialog? = null

fun showLoadingDialog(context: Context?) {
    hideLoadingDialog()
    if (context != null) {
        try {
            // Initialize the class
            loadingDialog = LoadingDialog(context)
            //setting dialog properties
            loadingDialog?.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun hideLoadingDialog() {
    //dismissing the dialog
    if (loadingDialog != null && loadingDialog?.isShowing!!) {
        loadingDialog = try {
            loadingDialog?.dismiss()
            null
        } catch (e: Exception) {
            null
        }
    }
}