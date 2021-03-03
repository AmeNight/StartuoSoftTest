package com.startupsoft.survey.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.startupsoft.survey.R
import com.startupsoft.survey.extentions.onClick
import kotlinx.android.synthetic.main.view_checkable_field.view.*

class CheckableFieldView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    var checked: Boolean = false
        set(value) {
            checkableFieldCheckbox.isChecked = value
            field = value
        }
        get() = checkableFieldCheckbox.isChecked

    var title: String
        set(value) {
            checkableFieldTitle.text = value
        }
        get() = checkableFieldTitle.text.toString()

    init {
        inflate(context, R.layout.view_checkable_field, this)
        parseAttributes(attrs)
    }

    fun onItemClick(itemClick: (Boolean) -> Unit) {
        checkableFieldRoot.onClick { itemClick(!checkableFieldCheckbox.isChecked) }
    }

    private fun parseAttributes(attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(
            attrs, R.styleable.CheckableFieldView, 0, 0
        )
        try {
            checkableFieldTitle.text = a.getString(R.styleable.CheckableFieldView_title).orEmpty()
        } finally {
            a.recycle()
        }
    }
}