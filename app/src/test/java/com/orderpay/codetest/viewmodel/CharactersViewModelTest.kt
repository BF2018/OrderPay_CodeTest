package com.orderpay.codetest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MediatorLiveData
import com.orderpay.codetest.repository.CharactersRepository
import com.orderpay.codetest.repository.LiveDataTestUtil
import com.orderpay.codetest.utils.networkutil.LoadingState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.RuntimeException

class CharactersViewModelTest {

    @Mock
    private lateinit var mRepo: CharactersRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    lateinit var characterViewModel: CharacterViewModel

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_refreshDataFromRepository_do_what_it_should_do() = runBlocking {

        //prep
        characterViewModel = CharacterViewModel(mRepo)

        val liveDataTestUtil = LiveDataTestUtil<LoadingState>()
        //action
        //when charactersListViewModel is created refreshDataFromRepository is called.
        //verify
        Mockito.verify(mRepo).refreshCharacters()
        val dataEmitted = liveDataTestUtil.getValue(characterViewModel.loadingState)
        assertEquals( LoadingState.Status.SUCCESS.name,dataEmitted?.status?.name)
    }

    @Test
    fun test_loading_error_is_called() = runBlocking {
        //prep
        //action
        //when charactersListViewModel is created refreshDataFromRepository is called.
        //verify
        Mockito.`when`(mRepo.refreshCharacters()).thenThrow(RuntimeException())
        characterViewModel = CharacterViewModel(mRepo)
        val mediatorLiveData = MediatorLiveData<LoadingState>()
        mediatorLiveData.addSource(characterViewModel.loadingState){
            when(it.status){
                 LoadingState.Status.LOADING -> {
                      //ignore
                 }
                else -> {
                    assertEquals( LoadingState.Status.ERROR.name,it?.status?.name)

                }
            }
        }
    }
}