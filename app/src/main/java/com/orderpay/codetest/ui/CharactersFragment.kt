package com.orderpay.codetest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.orderpay.codetest.R
import com.orderpay.codetest.databinding.FragmentCharactersBinding
import com.orderpay.codetest.ui.viewadapters.CharacterClick
import com.orderpay.codetest.ui.viewadapters.CharactersAdapter
import com.orderpay.codetest.utils.networkutil.LoadingState
import com.orderpay.codetest.viewmodel.CharacterViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersFragment : Fragment() {

    private var charactersAdapter: CharactersAdapter? = null
    private lateinit var binding: FragmentCharactersBinding

    /**
     * Here, by viewModel() creates the instance for the ViewModel and it will also resolve the dependency required by it.
     */
    private val characterViewModel: CharacterViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentCharactersBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = characterViewModel
        charactersAdapter = CharactersAdapter(CharacterClick {
            characterViewModel.displayPropertyDetails(it)
        })

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = charactersAdapter
        }

        characterViewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, {
            if (it != null) {
                this.findNavController()
                    .navigate(
                        CharactersFragmentDirections.actionCharactersFragmentToCharDetailFragment(
                            it
                        )
                    )
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                characterViewModel.displayPropertyDetailsComplete()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
    }


    private fun setupObserver() {

        characterViewModel.allItemsFiltered.observe(viewLifecycleOwner, { items ->
            // update the displayed items when the filtered results change
            items.let { charactersAdapter?.results = items }
        })

        characterViewModel.loadingState.observe(viewLifecycleOwner, {
            when (it.status) {
                LoadingState.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                }
                LoadingState.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                LoadingState.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE

                }
            }
        })
    }
}