package com.orderpay.codetest.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orderpay.codetest.R
import com.orderpay.codetest.databinding.FragmentCharacterDetailBinding
import com.orderpay.codetest.viewmodel.CharacterDetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CharacterDetailFragment : Fragment() {

    private val charDetailViewModel by viewModel<CharacterDetailViewModel>()

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
           val binding = FragmentCharacterDetailBinding.inflate(inflater)
           binding.lifecycleOwner = this@CharacterDetailFragment
           binding.viewModel = charDetailViewModel
           val selectedProperty = CharacterDetailFragmentArgs.fromBundle(arguments!!).selectedProperty
        if (selectedProperty != null) {
            charDetailViewModel.setProperty(selectedProperty)
        }
           return binding.root
    }

}