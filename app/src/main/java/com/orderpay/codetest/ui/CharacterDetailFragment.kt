package com.orderpay.codetest.ui

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

    val viewModel by viewModel<CharacterDetailViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       return  FragmentCharacterDetailBinding.inflate(inflater,container,false).apply {

        }.root
    }

}