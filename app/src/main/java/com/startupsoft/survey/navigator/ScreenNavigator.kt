package com.startupsoft.survey.navigator

import android.os.Bundle
import androidx.annotation.IdRes
import com.startupsoft.survey.base.BaseActivity
import com.startupsoft.survey.base.BaseFragment
import com.startupsoft.survey.navigator.ScreenId.*
import com.startupsoft.survey.ui.data_input.DataInputFragment
import com.startupsoft.survey.ui.intro.IntroFragment
import com.startupsoft.survey.ui.multi_choice.MultipleChoiceFragment
import java.io.Serializable

interface ScreenNavigator : Navigator

class ScreenNavigatorImpl(
    activity: BaseActivity, @IdRes containerId: Int = EMPTY
) : BaseNavigator(activity, containerId), ScreenNavigator {

    override fun openScreen(screenId: ScreenId, bundle: Bundle?, screenAnim: ScreenAnim?) {
        if (activityRef.get() == null) return
        when (screenId) {
            INTRO -> showFragment(prepareFragment(IntroFragment(), bundle), screenAnim, true)
            MULTI_CHOICE -> showFragment(prepareFragment(MultipleChoiceFragment(), bundle), screenAnim, true)
            DATA_INPUT -> showFragment(prepareFragment(DataInputFragment(), bundle), screenAnim, true)
        }
    }

    private fun <T : BaseFragment> prepareFragment(t: T, bundle: Bundle?) = t.apply { bundle?.let { t.arguments = it } }
}

enum class ScreenId : Serializable {
    INTRO,
    MULTI_CHOICE,
    DATA_INPUT
}