package com.startupsoft.survey.event_notifier

interface EventNotifier {
    fun notify(event: NotifiedEvent)
    fun subscribeForEvent(callback: OnNewEventConsumer)
    fun unsubscribeForEvent(callback: OnNewEventConsumer)
}

interface OnNewEventConsumer {
    fun onEvent(event: NotifiedEvent)
}