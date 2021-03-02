package com.startupsoft.survey.ui.helpers

import android.text.Editable
import android.text.TextWatcher

abstract class SimpleTextWatcher : TextWatcher {

    override fun afterTextChanged(s: Editable) = Unit
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit
    abstract override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)
}