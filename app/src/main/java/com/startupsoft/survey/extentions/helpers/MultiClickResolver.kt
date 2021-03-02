package com.startupsoft.survey.extentions.helpers

object MultiClickResolver {

    private const val MIN_CLICK_TIME_DIFF_MS = 150
    private var lastClickTime = 0L

    fun canProcessClick(): Boolean {
        val currentTime = System.currentTimeMillis()
        if(currentTime - lastClickTime >= MIN_CLICK_TIME_DIFF_MS) {
            lastClickTime = currentTime
            return true
        }
        return false
    }
}