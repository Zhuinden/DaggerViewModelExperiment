package com.zhuinden.daggerviewmodelexperiment.utils

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.lang.IllegalArgumentException

fun View.onClick(clickListener: (View) -> Unit) {
    setOnClickListener(clickListener)
}

tailrec fun Context.findActivity(): FragmentActivity {
    val context = this

    if (context is FragmentActivity) {
        return context
    }

    val baseContext = (context as ContextWrapper).baseContext
        ?: throw IllegalArgumentException("Context is not associated with an Activity")

    return baseContext.findActivity()
}

fun <T: FragmentActivity> Fragment.findActivity(context: Context = requireContext()): T {
    @Suppress("UNCHECKED_CAST")
    return context.findActivity() as T
}