package com.sword.health.view.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.sword.health.R

object ProgressDialog {

    private lateinit var dialog: Dialog

    fun init(context: Context) {
        dialog = Dialog(context)
        val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun show() {
        if (::dialog.isInitialized) {
            dialog.show()
        }
    }

    private fun isShowing(): Boolean {
        return if (::dialog.isInitialized) {
            dialog.isShowing
        } else {
            false
        }
    }

    fun hide() {
        if (::dialog.isInitialized && isShowing()) {
            dialog.hide()
        }
    }
}