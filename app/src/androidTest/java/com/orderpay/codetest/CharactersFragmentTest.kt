package com.orderpay.codetest

import androidx.lifecycle.MediatorLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.orderpay.codetest.TestUtils.withRecyclerView
import com.orderpay.codetest.database.CharacterEntity
import com.orderpay.codetest.ui.HomeActivity
import com.orderpay.codetest.util.EspressoIdlingResourceRule
import com.orderpay.codetest.util.FakeObjectsUtils
import com.orderpay.codetest.utils.networkutil.LoadingState
import com.orderpay.codetest.viewmodel.CharacterViewModel
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4ClassRunner::class)
class CharactersFragmentTest : KoinTest {

    @get:Rule
    val activityRule = ActivityTestRule(HomeActivity::class.java, true, false)
    //block the test until we have the results...
    @get: Rule
    val espressoIdlingResoureRule = EspressoIdlingResourceRule()

    @Mock
    private lateinit var charactersListViewModel: CharacterViewModel

    val fakeLoadingState= MediatorLiveData<LoadingState>()

    val fakeNavigateToSelectedProperty = MediatorLiveData<CharacterEntity>()

    var fakeAllItemsSearch = MediatorLiveData<List<CharacterEntity>>()

     var fakeAllItemsFiltered = MediatorLiveData<List<CharacterEntity>>()

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this);

        //return my fake object when loading state is called
        Mockito.doReturn(fakeNavigateToSelectedProperty).`when`(charactersListViewModel).navigateToSelectedProperty
        Mockito.doReturn(fakeAllItemsFiltered).`when`(charactersListViewModel).allItemsFiltered
        Mockito.doReturn(fakeLoadingState).`when`(charactersListViewModel).loadingState

        //INIT KOIN AND OVERRIDE THE VIEWMODEL
        loadKoinModules(
            module {

                viewModel(override = true){
                    charactersListViewModel
                }
            })
    }

    @Test
    fun test_mainActivityIsDisplayed() {
        launchActivity()
        emitLoadingState(LoadingState.LOADING)
        emitAllItemSearch(FakeObjectsUtils.listDBDatabaseCharacter)
        emitAllItemsFiltered(FakeObjectsUtils.listDBDatabaseCharacter)
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isCharacterListFragmentVisible() {
        launchActivity()
        emitLoadingState(LoadingState.LOADING)
        emitAllItemSearch(FakeObjectsUtils.listDBDatabaseCharacter)
        emitAllItemsFiltered(FakeObjectsUtils.listDBDatabaseCharacter)
        onView(withId(R.id.CharactersFragmentLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isRecyclerViewVisible() {
        launchActivity()
        emitLoadingState(LoadingState.LOADING)
        emitAllItemSearch(FakeObjectsUtils.listDBDatabaseCharacter)
        emitAllItemsFiltered(FakeObjectsUtils.listDBDatabaseCharacter)
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isCharacterDataVisible() {
        launchActivity()

        emitLoadingState(LoadingState.LOADING)
        emitAllItemSearch(FakeObjectsUtils.listDBDatabaseCharacter)
        emitAllItemsFiltered(FakeObjectsUtils.listDBDatabaseCharacter)

        val strNickname: String = "Sky"
        val strName: String = "Skyler White"

        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(0, R.id.title)).check(matches(withText(strName)))
        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(0, R.id.description)).check(matches(withText(strNickname)))
        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(0, R.id.video_thumbnail)).check(matches(isDisplayed()))

    }

    @Test
    fun test_isProgressBarShowing() {
        launchActivity()
        emitLoadingState(LoadingState.LOADING)
        emitAllItemSearch(FakeObjectsUtils.listDBDatabaseCharacter)
        emitAllItemsFiltered(FakeObjectsUtils.listDBDatabaseCharacter)

        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun test_isNotProgressBarIsShowingWhenSuccess() {
        launchActivity()
        emitLoadingState(LoadingState.SUCCESS)
        emitAllItemSearch(FakeObjectsUtils.listDBDatabaseCharacter)
        emitAllItemsFiltered(FakeObjectsUtils.listDBDatabaseCharacter)
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }


    private fun emitAllItemsFiltered(items: List<CharacterEntity>){
        fakeAllItemsFiltered.postValue(items)
    }

    private fun emitAllItemSearch(items: List<CharacterEntity>){
        fakeAllItemsSearch.postValue(items)
    }

    private fun emitLoadingState(loadingState: LoadingState){
        fakeLoadingState.postValue(loadingState)
    }
    private fun launchActivity() {
        activityRule.launchActivity(null)
    }


}