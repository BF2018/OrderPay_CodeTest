package com.orderpay.codetest.database

import com.orderpay.codetest.viewmodel.CharacterDetailViewModel
import com.orderpay.codetest.viewmodel.CharacterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CharacterViewModel(get())}
    viewModel { CharacterDetailViewModel()}
}