package com.startupsoft.survey.ui.multi_choice

import android.os.Bundle
import android.view.View
import com.startupsoft.survey.AppManager
import com.startupsoft.survey.R
import com.startupsoft.survey.base.BaseFragment
import com.startupsoft.survey.event_notifier.NotifiedEvent
import com.startupsoft.survey.event_notifier.NotifierEventType
import com.startupsoft.survey.extentions.removeDefaultAnimation
import com.startupsoft.survey.ui.multi_choice.adapter.MultipleChoiceAdapter
import com.startupsoft.survey.ui.multi_choice.adapter.MultipleChoiceViewModel
import kotlinx.android.synthetic.main.fragment_multi_choice.*

class MultipleChoiceFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_multi_choice
    private lateinit var adapter: MultipleChoiceAdapter

    private val surveyDataHolder = AppManager.surveyProgressDataHolder
    private val eventNotifier = AppManager.eventNotifier

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventNotifier.notify(NotifiedEvent(NotifierEventType.SURVEY_ONGOING))
        initAdapter()
    }

    private fun initAdapter() {
        adapter = MultipleChoiceAdapter { handleSelection(it) }
        multiChoiceItems.adapter = adapter
        adapter.setChoices(createChoicesList())
        multiChoiceItems.removeDefaultAnimation()
    }

    private fun handleSelection(type: MultipleChoiceType) {
        surveyDataHolder.state.type?.let { adapter.removeSelection(it) }
        surveyDataHolder.onMultiItemSelected(type)
    }

    private fun createChoicesList() = listOf(
        MultipleChoiceViewModel(
            MultipleChoiceType.FIRST,
            resources.getString(R.string.screen_multi_choice_1),
            surveyDataHolder.state.type == MultipleChoiceType.FIRST
        ),
        MultipleChoiceViewModel(
            MultipleChoiceType.SECOND,
            resources.getString(R.string.screen_multi_choice_2),
            surveyDataHolder.state.type == MultipleChoiceType.SECOND
        ),
        MultipleChoiceViewModel(
            MultipleChoiceType.THIRD,
            resources.getString(R.string.screen_multi_choice_3),
            surveyDataHolder.state.type == MultipleChoiceType.THIRD
        ),
        MultipleChoiceViewModel(
            MultipleChoiceType.FOURTH,
            resources.getString(R.string.screen_multi_choice_4),
            surveyDataHolder.state.type == MultipleChoiceType.FOURTH
        )
    )
}