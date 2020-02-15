package com.zhuinden.daggerviewmodelexperiment.application

import android.app.Application

class CustomApplication: Application(), ApplicationComponent.Provider {
    private lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.factory().create(this)
    }

    override val applicationComponent: ApplicationComponent
        get() = component
}