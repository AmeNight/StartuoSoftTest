package com.startupsoft.survey.base

import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.startupsoft.survey.navigator.Navigator

abstract class BaseActivity : AppCompatActivity() {
    abstract val navigator: Navigator

    @CallSuper
    override fun onBackPressed() {
        if (!navigator.notifyBackPress()) super.onBackPressed()
    }
}