package com.orderpay.codetest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.orderpay.codetest.R
import com.orderpay.codetest.databinding.FragmentCharactersBinding
import com.orderpay.codetest.ui.viewadapters.CharacterClick
import com.orderpay.codetest.ui.viewadapters.CharactersAdapter
import com.orderpay.codetest.utils.networkutil.LoadingState
import com.orderpay.codetest.viewmodel.CharacterViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersFragment : Fragment() {

    private var viewModelAdapter: CharactersAdapter? = null

    /**
     * Here, by viewModel() creates the instance for the ViewModel and it will also resolve the dependency required by it.
     */
    private val viewModel: CharacterViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCharactersBinding.inflate(inflater,container,false).apply{
            viewModelAdapter = CharactersAdapter(CharacterClick {
                viewModel.displayPropertyDetails(it)
            })

            recyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = viewModelAdapter
            }

        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
    }


    private fun setupObserver() {
        viewModel.loadingState.observe(viewLifecycleOwner, {
            when (it.status) {
                LoadingState.Status.ERROR -> {

                }
                LoadingState.Status.LOADING -> {

                }
                LoadingState.Status.SUCCESS -> {

                }
            }
    }) }
}