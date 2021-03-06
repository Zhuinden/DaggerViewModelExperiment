package com.zhuinden.daggerviewmodelexperiment.features.second

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.auto.factory.AutoFactory

@AutoFactory
class SecondViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val number: MutableLiveData<Int> = savedStateHandle.getLiveData("number", 0)

    fun number(): LiveData<Int> = number

    fun increment() {
        number.value = number.value!! + 1
    }
}