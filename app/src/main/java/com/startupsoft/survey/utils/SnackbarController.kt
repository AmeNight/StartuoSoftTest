package com.startupsoft.survey.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
import com.startupsoft.survey.R
import com.startupsoft.survey.extentions.onClick
import kotlinx.android.synthetic.main.view_bottom_snack_bar.view.*

private const val DEFAULT_MILLIS_DURATION = 3000L

class SnackbarController {
    private val handler = Handler(Looper.getMainLooper())

    fun showSnackBar(
        context: Context?, message: String, btnTitle: String? = null,
        onButtonClick: View.OnClickListener? = null
    ): SnackbarCancelable {
        return showSnackBarDialog(context, message, btnTitle, onButtonClick).let {
            setAutoDismiss(it)
            SnackbarCancelableImpl(it)
        }
    }

    private fun setAutoDismiss(dialog: Dialog) {
        handler.postDelayed({ dismiss(dialog) }, DEFAULT_MILLIS_DURATION)
    }

    private fun dismiss(dialog: Dialog) {
        val window = dialog.window ?: return
        val decor: View = window.decorView
        if (decor.parent != null) {
            dialog.dismiss()
        }
    }

    private fun showSnackBarDialog(
        context: Context?, message: String,
        btnTitle: String?, onButtonClick: View.OnClickListener?
    ): Dialog {
        val dialogView = createSnackBarDialogView(context, message, btnTitle, onButtonClick)
        val builder: AlertDialog.Builder = AlertDialog.Builder(context!!, R.style.SnackBarDialogTheme)
        builder.setView(dialogView)
        val alertDialog = builder.setCancelable(false).create()
        alertDialog.window?.setGravity(Gravity.BOTTOM)
        alertDialog.window?.setFlags(FLAG_NOT_FOCUSABLE, FLAG_NOT_FOCUSABLE)
        return alertDialog.apply { show() }
    }

    private fun createSnackBarDialogView(
        context: Context?, message: String,
        btnTitle: String?, onButtonClick: View.OnClickListener?
    ): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.view_bottom_snack_bar, null, false)
        view.messageSnackbarView.text = message
        view.btnSnackbarAction.text = btnTitle
        view.btnSnackbarAction.onClick { onButtonClick?.onClick(it) }
        return view
    }
}

interface SnackbarCancelable {
    fun canCancel(): Boolean
    fun cancel()
}

private class SnackbarCancelableImpl(private var dialog: Dialog?) : SnackbarCancelable {

    init {
        dialog?.setOnDismissListener {
            dialog = null
        }
    }

    override fun cancel() {
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
            dialog = null
        }
    }

    override fun canCancel() = dialog != null

}