package com.zhuinden.daggerviewmodelexperiment.features.second

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.zhuinden.daggerviewmodelexperiment.R
import com.zhuinden.daggerviewmodelexperiment.application.SharedViewModel
import com.zhuinden.daggerviewmodelexperiment.application.activityComponent
import com.zhuinden.daggerviewmodelexperiment.features.second.SecondFragment
import com.zhuinden.daggerviewmodelexperiment.features.second.SecondViewModel
import com.zhuinden.daggerviewmodelexperiment.features.second.SecondViewModelFactory
import com.zhuinden.daggerviewmodelexperiment.utils.FragmentScope
import com.zhuinden.daggerviewmodelexperiment.utils.onClick
import dagger.BindsInstance
import dagger.Provides
import kotlinx.android.synthetic.main.second_fragment.*
import javax.inject.Inject

class SecondFragment : Fragment(R.layout.second_fragment) {
    @FragmentScope
    @dagger.Subcomponent(modules = [Module::class])
    interface Component {
        fun inject(secondFragment: SecondFragment)

        @dagger.Subcomponent.Factory
        interface Factory {
            fun create(@BindsInstance fragment: SecondFragment): Component
        }
    }

    @dagger.Module
    class Module {
        @Provides
        @Suppress("UNCHECKED_CAST")
        fun viewModel(secondFragment: SecondFragment, factory: SecondViewModelFactory): SecondViewModel =
            ViewModelProvider(
                secondFragment,
                object :
                    AbstractSavedStateViewModelFactory(secondFragment, secondFragment.arguments) {
                    override fun <T : ViewModel?> create(
                        key: String,
                        modelClass: Class<T>,
                        handle: SavedStateHandle
                    ): T = factory.create(handle) as T
                }).get()
    }

    private lateinit var component: Component

    @Inject
    lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var viewModel: SecondViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        component = activityComponent.secondFragmentComponentFactory().create(this)
        component.inject(this) // cannot be in onAttach because of SavedStateHandle

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.number().observe(viewLifecycleOwner) { number ->
            textActivityNumber.text = "Activity: $number"
        }

        viewModel.number().observe(viewLifecycleOwner) { number ->
            textFragmentNumber.text = "Fragment: $number"
        }

        buttonIncrementFragmentViewModel.onClick {
            viewModel.increment()
        }

        buttonIncrementActivityViewModel.onClick {
            sharedViewModel.increment()
        }
    }
}