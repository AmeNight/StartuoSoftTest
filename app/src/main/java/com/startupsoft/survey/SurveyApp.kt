package com.startupsoft.survey

import android.app.Application
import android.content.Context
import com.startupsoft.survey.data_holders.SurveyProgressDataHolder
import com.startupsoft.survey.data_holders.SurveyProgressDataHolderImpl
import com.startupsoft.survey.event_notifier.EventNotifier
import com.startupsoft.survey.event_notifier.EventNotifierImpl
import com.startupsoft.survey.utils.ThreadUtils

class SurveyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppManager.init(this)
    }
}

object AppManager {
    lateinit var context: Context

    lateinit var surveyProgressDataHolder: SurveyProgressDataHolder
    lateinit var eventNotifier: EventNotifier

    fun init(application: Application) {
        ThreadUtils().checkOnMainThread()
        initDependencyProviders(application)
    }

    private fun initDependencyProviders(application: Application) {
        context = application.applicationContext
        surveyProgressDataHolder = SurveyProgressDataHolderImpl()
        eventNotifier = EventNotifierImpl()
    }
}