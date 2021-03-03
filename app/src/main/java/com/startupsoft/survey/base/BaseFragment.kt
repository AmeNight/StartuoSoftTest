package com.startupsoft.survey.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.startupsoft.survey.navigator.Navigator

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int
    val navigator: Navigator by lazy(LazyThreadSafetyMode.NONE) { (activity as BaseActivity).navigator }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }
}