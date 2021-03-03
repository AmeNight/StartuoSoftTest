package com.startupsoft.survey.ui.intro

import android.os.Bundle
import android.view.View
import com.startupsoft.survey.AppManager
import com.startupsoft.survey.R
import com.startupsoft.survey.base.BaseFragment
import com.startupsoft.survey.event_notifier.NotifiedEvent
import com.startupsoft.survey.event_notifier.NotifierEventType
import com.startupsoft.survey.extentions.onClick
import com.startupsoft.survey.navigator.ScreenAnim
import com.startupsoft.survey.navigator.ScreenId
import com.startupsoft.survey.utils.SnackbarCancelable
import com.startupsoft.survey.utils.SnackbarController
import kotlinx.android.synthetic.main.fragment_intro.*

const val FROM_PREVIOUS_FLOW = "from_previous_flow"

class IntroFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_intro

    private val surveyDataHolder = AppManager.surveyProgressDataHolder
    private val eventNotifier = AppManager.eventNotifier

    private var snackbarCancelable: SnackbarCancelable? = null

    private val fromPreviousFlow: Boolean
        get() = arguments?.getBoolean(FROM_PREVIOUS_FLOW, false) ?: false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventNotifier.notify(NotifiedEvent(NotifierEventType.SURVEY_PREPARATION))
        initOnClicks()
        handleIsFromPreviousFlow()
    }

    private fun initOnClicks() {
        introAction?.onClick { onActionClick() }
    }

    private fun handleIsFromPreviousFlow() {
        if (fromPreviousFlow) {
            snackbarCancelable = SnackbarController().showSnackBar(
                context, resources.getString(R.string.screen_intro_sent_message),
                resources.getString(R.string.screen_intro_undo_action),
                View.OnClickListener { navigator.undoLastClear() }
            )
        }
    }

    override fun onStop() {
        super.onStop()
        if (snackbarCancelable?.canCancel() == true) {
            snackbarCancelable?.cancel()
        }
    }

    private fun onActionClick() {
        if (fromPreviousFlow) surveyDataHolder.invalidate()
        navigator.openScreen(ScreenId.MULTI_CHOICE, screenAnim = ScreenAnim.horizontalEnter)
    }
}