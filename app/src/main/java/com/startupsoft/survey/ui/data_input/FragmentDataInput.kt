package com.startupsoft.survey.ui.data_input

import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import com.startupsoft.survey.AppManager
import com.startupsoft.survey.R
import com.startupsoft.survey.base.BaseFragment
import com.startupsoft.survey.event_notifier.NotifiedEvent
import com.startupsoft.survey.event_notifier.NotifierEventType
import com.startupsoft.survey.extentions.insert
import com.startupsoft.survey.extentions.toDoubleOrZero
import com.startupsoft.survey.ui.helpers.SimpleTextWatcher
import com.startupsoft.survey.utils.KeyboardUtils
import com.startupsoft.survey.utils.NumberUtils
import kotlinx.android.synthetic.main.fragment_data_input.*

class FragmentDataInput : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_data_input

    private val keyboardUtils = KeyboardUtils()
    private val numberUtils = NumberUtils()

    private val eventNotifier = AppManager.eventNotifier

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventNotifier.notify(NotifiedEvent(NotifierEventType.SURVEY_ONGOING))
        initInputTextFormatting()
        keyboardUtils.showKeyboard(context)
        dataInputEditText.requestFocus()
    }

    private fun initInputTextFormatting() {
        dataInputEditText.addTextChangedListener(onTextChangedListener())
    }

    private fun onTextChangedListener(): TextWatcher = object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            dataInputEditText.removeTextChangedListener(this)
            val contentWithoutComma = s.toString().replace(",", "")
            val contentWithFormattedComma = contentWithoutComma.insert(".", contentWithoutComma.length - 2)
            val formattedResult = numberUtils.formatNumber(contentWithFormattedComma.toDoubleOrZero()).replace(".", ",")
            dataInputEditText.setText(formattedResult)
            dataInputEditText.addTextChangedListener(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        keyboardUtils.hideKeyboard(context, view)
    }
}