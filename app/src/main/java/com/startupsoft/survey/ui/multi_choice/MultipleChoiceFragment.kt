package com.startupsoft.survey.ui.multi_choice

import android.os.Bundle
import android.view.View
import com.startupsoft.survey.AppManager
import com.startupsoft.survey.R
import com.startupsoft.survey.base.BaseFragment
import com.startupsoft.survey.event_notifier.NotifiedEvent
import com.startupsoft.survey.event_notifier.NotifierEventType
import com.startupsoft.survey.extentions.removeDefaultAnimation
import com.startupsoft.survey.navigator.ScreenAnim
import com.startupsoft.survey.navigator.ScreenId
import com.startupsoft.survey.ui.multi_choice.adapter.MultipleChoiceAdapter
import com.startupsoft.survey.ui.multi_choice.adapter.MultipleChoiceViewModel
import kotlinx.android.synthetic.main.fragment_multi_choice.*

class MultipleChoiceFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_multi_choice
    private lateinit var adapter: MultipleChoiceAdapter

    private var currentType: MultipleChoiceType? = null

    private val surveyDataHolder = AppManager.surveyProgressDataHolder
    private val eventNotifier = AppManager.eventNotifier

    private var isItemAlreadyClicked = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventNotifier.notify(NotifiedEvent(NotifierEventType.SURVEY_ONGOING))
        currentType = surveyDataHolder.state.type
        initAdapter()
    }

    private fun initAdapter() {
        isItemAlreadyClicked = false
        adapter = MultipleChoiceAdapter { type, checked -> handleSelection(type, checked) }
        multiChoiceItems.adapter = adapter
        adapter.setChoices(createChoicesList())
        multiChoiceItems.removeDefaultAnimation()
    }

    private fun handleSelection(type: MultipleChoiceType, checked: Boolean) {
        if (!checked) {
            adapter.setSelected(type, false)
            currentType = null
            return
        }
        if (isItemAlreadyClicked) return
        isItemAlreadyClicked = true
        currentType?.let { adapter.setSelected(it, false) }
        adapter.setSelected(type, true)
        surveyDataHolder.onMultiItemSelected(type)
        view?.postDelayed({ navigator.openScreen(ScreenId.DATA_INPUT, screenAnim = ScreenAnim.horizontalEnter) }, 1000)
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