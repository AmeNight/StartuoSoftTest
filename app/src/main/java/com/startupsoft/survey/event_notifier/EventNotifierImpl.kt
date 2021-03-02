package com.startupsoft.survey.event_notifier

class EventNotifierImpl : EventNotifier {

    private val consumers: MutableList<OnNewEventConsumer> = ArrayList()

    override fun notify(event: NotifiedEvent) {
        consumers.forEach { it.onEvent(event) }
    }

    override fun subscribeForEvent(callback: OnNewEventConsumer) {
        consumers += callback
    }

    override fun unsubscribeForEvent(callback: OnNewEventConsumer) {
        consumers -= callback
    }
}

data class NotifiedEvent(val type: NotifierEventType)

enum class NotifierEventType {
    SURVEY_PREPARATION,
    SURVEY_ONGOING
}