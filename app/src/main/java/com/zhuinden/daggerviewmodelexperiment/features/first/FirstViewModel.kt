package com.zhuinden.daggerviewmodelexperiment.features.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided

@AutoFactory
class FirstViewModel(
    @Provided private val firstClass: FirstClass,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val number: MutableLiveData<Int> = savedStateHandle.getLiveData("number", firstClass.initialNumber)

    fun number(): LiveData<Int> = number

    fun increment() {
        number.value = number.value!! + 1
    }
}