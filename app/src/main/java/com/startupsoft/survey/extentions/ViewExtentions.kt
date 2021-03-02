package com.startupsoft.survey.extentions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.startupsoft.survey.extentions.helpers.MultiClickResolver

inline fun View.onClick(crossinline f: (v: View) -> Unit) = setOnClickListener {
    if (MultiClickResolver.canProcessClick()) {
        f(it)
    }
}

fun ViewGroup.inflateView(@LayoutRes resource: Int): View {
    return LayoutInflater.from(this.context).inflate(resource, this, false)
}

fun RecyclerView.removeDefaultAnimation() {
    (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
}