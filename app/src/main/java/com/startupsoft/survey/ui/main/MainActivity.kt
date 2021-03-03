package com.startupsoft.survey.ui.main

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.startupsoft.survey.AppManager
import com.startupsoft.survey.R
import com.startupsoft.survey.base.BaseActivity
import com.startupsoft.survey.event_notifier.NotifiedEvent
import com.startupsoft.survey.event_notifier.NotifierEventType
import com.startupsoft.survey.event_notifier.OnNewEventConsumer
import com.startupsoft.survey.navigator.Navigator
import com.startupsoft.survey.navigator.ScreenId
import com.startupsoft.survey.navigator.ScreenNavigatorImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), OnNewEventConsumer {

    override val navigator: Navigator = ScreenNavigatorImpl(this, R.id.mainFragmentContainer)
    private val eventNotifier = AppManager.eventNotifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        eventNotifier.subscribeForEvent(this)
        initOnClicks()
        navigator.openScreen(ScreenId.INTRO)
    }

    private fun initOnClicks() {
        mainToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onEvent(event: NotifiedEvent) {
        if (event.type == NotifierEventType.SURVEY_PREPARATION) {
            mainToolbar.navigationIcon = null
        } else if (event.type == NotifierEventType.SURVEY_ONGOING) {
            mainToolbar.navigationIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_arrow, null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventNotifier.unsubscribeForEvent(this)
    }
}