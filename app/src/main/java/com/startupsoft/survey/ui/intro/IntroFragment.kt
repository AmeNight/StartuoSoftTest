package com.startupsoft.survey.ui.intro

import android.os.Bundle
import android.view.View
import com.startupsoft.survey.AppManager
import com.startupsoft.survey.R
import com.startupsoft.survey.base.BaseFragment
import com.startupsoft.survey.event_notifier.NotifiedEvent
import com.startupsoft.survey.event_notifier.NotifierEventType
import com.startupsoft.survey.extentions.onClick
import kotlinx.android.synthetic.main.fragment_intro.*

class IntroFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_intro

    private val surveyDataHolder = AppManager.surveyProgressDataHolder
    private val eventNotifier = AppManager.eventNotifier

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventNotifier.notify(NotifiedEvent(NotifierEventType.SURVEY_PREPARATION))
        initOnClicks()
    }

    private fun initOnClicks() {
        introAction?.onClick { surveyDataHolder.invalidate() }
    }
}