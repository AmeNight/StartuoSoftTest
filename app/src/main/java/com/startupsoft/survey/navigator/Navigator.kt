package com.startupsoft.survey.navigator

import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import com.startupsoft.survey.R
import com.startupsoft.survey.base.BaseActivity
import com.startupsoft.survey.base.BaseFragment
import java.lang.ref.WeakReference
import java.util.*

interface Navigator {
    fun registerBackPressObserver(observer: BackPressObserver)
    fun unregisterBackPressObserver(observer: BackPressObserver)
    fun notifyBackPress(): Boolean
    fun openScreen(screenId: ScreenId, bundle: Bundle? = null, screenAnim: ScreenAnim? = null)
    fun popFragmentStack()
    fun clearFragmentStack()
    fun undoLastClear()
}

interface BackPressObserver {
    fun onBackPress(): Boolean
}

data class ScreenAnim(
    @AnimRes val animIn: Int, @AnimRes val animOut: Int,
    @AnimRes val animPopIn: Int? = null, @AnimRes val animPopOut: Int? = null
) {
    companion object DefaultAnimations {
        val horizontalEnter = ScreenAnim(
            R.anim.animation_enter_horizontal_slide, R.anim.animation_exit_horizontal_slide,
            R.anim.animation_pop_enter_horizontal_slide, R.anim.animation_pop_exit_horizontal_slide
        )
    }
}

const val EMPTY = -1

abstract class BaseNavigator(activity: BaseActivity, @IdRes protected val containerId: Int = EMPTY) : Navigator {
    protected var activityRef: WeakReference<BaseActivity> = WeakReference(activity)
    private val fragmentStack = LinkedList<Pair<BaseFragment, ScreenAnim?>>()
    private val previousFragmentStack = LinkedList<Pair<BaseFragment, ScreenAnim?>>()
    private val observers = ArrayList<BackPressObserver>()

    override fun notifyBackPress(): Boolean {
        observers.forEach {
            if (it.onBackPress()) {
                return true
            }
        }
        return popStack()
    }

    override fun clearFragmentStack() {
        previousFragmentStack.clear()
        previousFragmentStack.addAll(fragmentStack)
        fragmentStack.clear()
    }

    override fun undoLastClear() {
        val fragmentLast = fragmentStack.last

        fragmentStack.clear()
        fragmentStack += previousFragmentStack
        previousFragmentStack.clear()

        val pairToShow = fragmentStack.last.apply { first.arguments = null }
        addFragment(pairToShow.first, fragmentLast.second?.animPopIn, fragmentLast.second?.animPopOut)
        removeFragment(fragmentLast.first)
    }

    override fun popFragmentStack() {
        popStack()
    }

    override fun registerBackPressObserver(observer: BackPressObserver) {
        observers += observer
    }

    override fun unregisterBackPressObserver(observer: BackPressObserver) {
        observers.remove(observer)
    }

    private fun popStack() = if (fragmentStack.size > 1) {
        val fragmentLast = fragmentStack.last
        fragmentStack.removeLast()
        val pairToShow = fragmentStack.last.apply { first.arguments = null }
        addFragment(pairToShow.first, fragmentLast.second?.animPopIn, fragmentLast.second?.animPopOut)
        removeFragment(fragmentLast.first)
        true
    } else {
        false
    }

    protected fun showFragment(fragment: BaseFragment, screenAnim: ScreenAnim?, toBackStack: Boolean = false) {
        if (containerId == EMPTY) {
            throw IllegalStateException("You need determinate container id to attach fragments to activity")
        }

        if (toBackStack) {
            fragmentStack += Pair(fragment, screenAnim)
        }
        addFragment(fragment, screenAnim?.animIn, screenAnim?.animOut)
    }

    private fun addFragment(fragment: BaseFragment, @AnimRes animIn: Int? = null, @AnimRes animOut: Int? = null) {
        activityRef.get()?.supportFragmentManager?.beginTransaction()?.apply {
            if (animIn != null && animOut != null) {
                setCustomAnimations(animIn, animOut)
            }
            replace(containerId, fragment).commitAllowingStateLoss()
        }
    }

    protected fun removeFragment(fragment: BaseFragment) {
        activityRef.get()?.supportFragmentManager?.beginTransaction()?.remove(fragment)
            ?.commitAllowingStateLoss()
    }
}