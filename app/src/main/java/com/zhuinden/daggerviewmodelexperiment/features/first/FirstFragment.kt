package com.zhuinden.daggerviewmodelexperiment.features.first

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.*
import com.zhuinden.daggerviewmodelexperiment.R
import com.zhuinden.daggerviewmodelexperiment.application.SharedViewModel
import com.zhuinden.daggerviewmodelexperiment.application.activityComponent
import com.zhuinden.daggerviewmodelexperiment.features.second.SecondFragment
import com.zhuinden.daggerviewmodelexperiment.utils.FragmentScope
import com.zhuinden.daggerviewmodelexperiment.utils.onClick
import dagger.BindsInstance
import dagger.Provides
import kotlinx.android.synthetic.main.first_fragment.*
import javax.inject.Inject

class FirstFragment : Fragment(R.layout.first_fragment) {
    @FragmentScope
    @dagger.Subcomponent(modules = [Module::class])
    interface Component {
        @dagger.Subcomponent.Factory
        interface Factory {
            fun create(@BindsInstance fragment: FirstFragment): Component
        }

        fun inject(fragment: FirstFragment)
    }

    @dagger.Module
    class Module {
        @Provides
        @Suppress("UNCHECKED_CAST")
        fun viewModel(firstFragment: FirstFragment, factory: FirstViewModelFactory): FirstViewModel =
            ViewModelProvider(
                firstFragment,
                object :
                    AbstractSavedStateViewModelFactory(firstFragment, firstFragment.arguments) {
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
    lateinit var viewModel: FirstViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        component = activityComponent.firstFragmentComponentFactory().create(this)
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

        buttonGoToSecond.onClick {
            parentFragmentManager.commit {
                replace(R.id.container, SecondFragment())
                addToBackStack(null) // blah
            }
        }

        buttonIncrementFragmentViewModel.onClick {
            viewModel.increment()
        }

        buttonIncrementActivityViewModel.onClick {
            sharedViewModel.increment()
        }
    }
}