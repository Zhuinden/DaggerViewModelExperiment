package com.zhuinden.daggerviewmodelexperiment.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.zhuinden.daggerviewmodelexperiment.R
import com.zhuinden.daggerviewmodelexperiment.features.first.FirstFragment

class MainActivity : AppCompatActivity(), ActivityComponent.Provider {
    private lateinit var component: ActivityComponent
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        component = applicationComponent.activityComponentFactory().create(this) // must be above super.onCreate

        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.container, FirstFragment())
            }
        }
    }

    override val activityComponent: ActivityComponent
        get() = component
}

val Fragment.activityComponent: ActivityComponent
    get() = (requireActivity() as ActivityComponent.Provider).activityComponent