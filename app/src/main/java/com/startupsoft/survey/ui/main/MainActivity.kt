package com.startupsoft.survey.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.startupsoft.survey.AppManager
import com.startupsoft.survey.R
import com.startupsoft.survey.event_notifier.NotifiedEvent
import com.startupsoft.survey.event_notifier.NotifierEventType
import com.startupsoft.survey.event_notifier.OnNewEventConsumer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnNewEventConsumer {

    private val eventNotifier = AppManager.eventNotifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        eventNotifier.subscribeForEvent(this)
    }

    override fun onEvent(event: NotifiedEvent) {
        if (event.type == NotifierEventType.SURVEY_PREPARATION) {
            mainToolbar.navigationIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_arrow, null)
        } else if (event.type == NotifierEventType.SURVEY_ONGOING) {
            mainToolbar.navigationIcon = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventNotifier.unsubscribeForEvent(this)
    }
}