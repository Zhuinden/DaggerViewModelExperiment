package com.zhuinden.daggerviewmodelexperiment.application

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhuinden.daggerviewmodelexperiment.features.first.FirstFragment
import com.zhuinden.daggerviewmodelexperiment.features.second.SecondFragment
import com.zhuinden.daggerviewmodelexperiment.utils.ActivityScope
import dagger.BindsInstance
import dagger.Provides
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        FragmentBuilderModule::class,
        ActivityComponent.Module::class
    ]
)
interface ActivityComponent {
    fun firstFragmentComponentFactory(): FirstFragment.Component.Factory // good chance this should be multi-bound by common interface, like AndroidInjector<T>
    fun secondFragmentComponentFactory(): SecondFragment.Component.Factory // good chance this should be multi-bound by common interface, like AndroidInjector<T>

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: FragmentActivity): ActivityComponent
    }

    @dagger.Module
    class Module {
        @Provides
        @Suppress("UNCHECKED_CAST")
        fun sharedViewModel(activity: FragmentActivity): SharedViewModel =
            ViewModelProvider(
                activity,
                object : AbstractSavedStateViewModelFactory(activity, activity.intent.extras) {
                    override fun <T : ViewModel?> create(
                        key: String,
                        modelClass: Class<T>,
                        handle: SavedStateHandle
                    ): T = SharedViewModelFactory().create(handle) as T
                }).get(SharedViewModel::class.java)
    }

    interface Provider {
        val activityComponent: ActivityComponent
    }
}