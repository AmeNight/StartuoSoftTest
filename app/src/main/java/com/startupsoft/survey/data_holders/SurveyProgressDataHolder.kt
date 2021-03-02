package com.startupsoft.survey.data_holders

import com.startupsoft.survey.ui.multi_choice.MultipleChoiceType

interface SurveyProgressDataHolder {
    val state: SurveyProgressState
    fun onMultiItemSelected(type: MultipleChoiceType)
    fun onAmountInserted(amount: String)
    fun invalidate()
}