package com.orderpay.codetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orderpay.codetest.database.CharacterEntity

class CharacterDetailViewModel : ViewModel() {

    // The internal MutableLiveData for the selected character
    private val _selectedProperty = MutableLiveData<CharacterEntity>()

    // The external LiveData for the SelectedCharacter
    val selectedProperty: LiveData<CharacterEntity>
        get() = _selectedProperty


    fun setProperty(characterProperty: CharacterEntity) {
        _selectedProperty.value = characterProperty
    }
}