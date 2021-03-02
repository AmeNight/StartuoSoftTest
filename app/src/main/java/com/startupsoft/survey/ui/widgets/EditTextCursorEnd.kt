package com.startupsoft.survey.ui.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class EditTextCursorEnd(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        this.setSelection(this.text!!.length)
    }
}