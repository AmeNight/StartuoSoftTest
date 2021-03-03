package com.startupsoft.survey.ui.data_input

import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import com.startupsoft.survey.AppManager
import com.startupsoft.survey.R
import com.startupsoft.survey.base.BaseFragment
import com.startupsoft.survey.event_notifier.NotifiedEvent
import com.startupsoft.survey.event_notifier.NotifierEventType
import com.startupsoft.survey.extentions.insert
import com.startupsoft.survey.extentions.onClick
import com.startupsoft.survey.extentions.toDoubleOrZero
import com.startupsoft.survey.navigator.ScreenAnim
import com.startupsoft.survey.navigator.ScreenId
import com.startupsoft.survey.ui.helpers.SimpleTextWatcher
import com.startupsoft.survey.ui.intro.FROM_PREVIOUS_FLOW
import com.startupsoft.survey.utils.KeyboardUtils
import com.startupsoft.survey.utils.NumberUtils
import kotlinx.android.synthetic.main.fragment_data_input.*

class DataInputFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_data_input

    private val keyboardUtils = KeyboardUtils()
    private val numberUtils = NumberUtils()

    private val surveyDataHolder = AppManager.surveyProgressDataHolder
    private val eventNotifier = AppManager.eventNotifier

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventNotifier.notify(NotifiedEvent(NotifierEventType.SURVEY_ONGOING))
        initViews()
        initOnClicks()
    }

    private fun initViews() {
        dataInputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onActionClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        surveyDataHolder.state.amount?.let { dataInputEditText.setText(it) }
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

    private fun initOnClicks() {
        dataInputAction.onClick { onActionClick() }
    }

    private fun onActionClick() {
        surveyDataHolder.onAmountInserted(dataInputEditText.text.toString())
        navigator.clearFragmentStack()
        navigator.openScreen(
            ScreenId.INTRO, Bundle().apply { putBoolean(FROM_PREVIOUS_FLOW, true) },
            screenAnim = ScreenAnim.horizontalEnter
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        keyboardUtils.hideKeyboard(context, view)
    }
}