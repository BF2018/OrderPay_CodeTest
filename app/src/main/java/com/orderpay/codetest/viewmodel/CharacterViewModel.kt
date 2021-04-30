package com.orderpay.codetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.orderpay.codetest.database.CharacterEntity
import com.orderpay.codetest.repository.CharactersRepository
import com.orderpay.codetest.utils.OpenForTesting
import com.orderpay.codetest.utils.networkutil.LoadingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception

@OpenForTesting
class CharacterViewModel(private val charactersRepo: CharactersRepository) : ViewModel() {


    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    /**
     * This is the job for all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        refreshDataFromRepository()
    }


    /**
     * Refresh data from the repository. Use a coroutine launch to run in a background thread.
     */
    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                charactersRepo.refreshCharacters()
                _loadingState.value = LoadingState.SUCCESS

            } catch (networkError: Exception) {
                _loadingState.value = LoadingState.error(networkError.message)
            }
        }
    }

    //for display data in second fragment
    // LiveData to handle navigation to the selected Character
    private val _navigateToSelectedProperty = MutableLiveData<CharacterEntity>()
    val navigateToSelectedProperty: LiveData<CharacterEntity>
        get() = _navigateToSelectedProperty

    /**
     * When the character is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param characterProperty The [Character] that was clicked on.
     */

    fun displayPropertyDetails(characterProperty: CharacterEntity) {
        _navigateToSelectedProperty.value = characterProperty
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }



    /**
     * For Filter Feature based on Appearance
     */
    lateinit var allItemsFiltered: LiveData<List<CharacterEntity>>
    var filter = MutableLiveData<String>("%")

    init {
        allItemsFiltered = Transformations.switchMap(filter) { filter ->
            charactersRepo.getItemsByAppearance(filter)
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}