package com.startupsoft.survey.data_holders

import com.startupsoft.survey.ui.multi_choice.MultipleChoiceType

class SurveyProgressDataHolderImpl : SurveyProgressDataHolder {

    override var state: SurveyProgressState = SurveyProgressState()

    override fun onMultiItemSelected(type: MultipleChoiceType) {
        state = state.copy(type = type)
    }

    override fun onAmountInserted(amount: String) {
        state = state.copy(amount = amount)
    }

    override fun invalidate() {
        state = SurveyProgressState()
    }
}

data class SurveyProgressState(
    val type: MultipleChoiceType? = null,
    val amount: String? = null
)