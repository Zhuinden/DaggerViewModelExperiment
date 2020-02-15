package com.zhuinden.daggerviewmodelexperiment.application

import com.zhuinden.daggerviewmodelexperiment.features.first.FirstFragment
import com.zhuinden.daggerviewmodelexperiment.features.second.SecondFragment
import dagger.Module

@Module(
    includes = [
        FirstFragment.Module::class,
        SecondFragment.Module::class
    ]
)
object FragmentBuilderModule {
}