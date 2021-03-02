package com.startupsoft.survey.utils

import android.os.Looper
import java.lang.Thread.currentThread

class ThreadUtils {
    fun checkOnMainThread() {
        if (Looper.getMainLooper().thread != currentThread()) {
            throw IllegalThreadStateException("Operation allowed on main thread only")
        }
    }
}