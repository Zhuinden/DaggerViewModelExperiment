package com.zhuinden.daggerviewmodelexperiment.application

import android.app.Application
import androidx.fragment.app.FragmentActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface ApplicationComponent {
    fun activityComponentFactory(): ActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): ApplicationComponent
    }

    interface Provider {
        val applicationComponent: ApplicationComponent
    }
}

val FragmentActivity.applicationComponent
    get() = (applicationContext as ApplicationComponent.Provider).applicationComponent