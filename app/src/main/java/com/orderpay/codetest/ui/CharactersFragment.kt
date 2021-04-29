package com.orderpay.codetest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private var viewModelAdapter: CharactersAdapter? = null
    private lateinit var binding : FragmentCharactersBinding
    private lateinit var v: View
    /**
     * Here, by viewModel() creates the instance for the ViewModel and it will also resolve the dependency required by it.
     */
    private val viewModel: CharacterViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCharactersBinding.inflate(inflater,container,false).apply{
            binding = this
            v = this.root.findViewById(R.id.CharactersFragmentLayout)
            viewModelAdapter = CharactersAdapter(CharacterClick {
                viewModel?.displayPropertyDetails(it)
            })

            recyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = viewModelAdapter
            }

            viewModel?.navigateToSelectedProperty?.observe(viewLifecycleOwner, {
                if (null != it) {
                    this@CharactersFragment.findNavController().navigate(R.id.CharacterDetailFragment)
                       // CharactersFragmentDirections.actionCharactersFragmentToCharDetailFragment(it)

                    // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                    viewModel?.displayPropertyDetailsComplete()
                }
            })
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
                    binding.progressBar.visibility = View.GONE
                }
                LoadingState.Status.LOADING -> {
                  binding.progressBar.visibility = View.VISIBLE
                }
                LoadingState.Status.SUCCESS -> {
                  binding.progressBar.visibility = View.GONE
                }
            }
    }) }
}