package com.orderpay.codetest.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.orderpay.codetest.database.CharacterEntity
import com.orderpay.codetest.network.ApiServices
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CharactersRepositoryTest : DatabaseTest() {

    private lateinit var mRepo: CharactersRepository

    @Mock
    lateinit var breakingbadApiservices: ApiServices


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun init() {
        super.init()
        MockitoAnnotations.initMocks(this);

        mRepo = CharactersRepository(
            breakingbadApiservices,
            database
        )
    }

    @Test
    fun test_character_repo_retrieves_expected_data() =
        runBlocking {
            val fakeToReturn =
                CompletableDeferred(FakeObjectsUtils.listDBDatabaseCharacter)

            Mockito.`when`(breakingbadApiservices.getAllCharacters())
                .thenReturn(fakeToReturn)

            val dataReceived = mRepo.refreshCharacters()

            assertNotNull(dataReceived)
        }



    @Test
    fun test_getItemsByAppearance_return_expected_value() {
        //prep
        val fakeObject = FakeObjectsUtils.listDBDatabaseCharacter
        database.characterDao.insertAll(fakeObject)
        val liveDataUtils = LiveDataTestUtil<List<CharacterEntity>>()
        val appearance = "%1%"
        //action
        val dataReceived = mRepo.getItemsByAppearance(appearance)
        val dataReturned = liveDataUtils.getValue(dataReceived)?.get(0)

        //verify
        assertNotNull(dataReceived)
        assertNotNull(dataReturned)

        assertEquals(fakeObject[0].name, dataReturned?.name)
        assertEquals(fakeObject[0].char_id, dataReturned?.char_id)
        assertEquals(fakeObject[0].appearance, dataReturned?.appearance)
        assertEquals(fakeObject[0].nickname, dataReturned?.nickname)
        assertEquals(fakeObject[0].img, dataReturned?.img)
        assertEquals(fakeObject[0].occupation, dataReturned?.occupation)
        assertEquals(fakeObject[0].status, dataReturned?.status)
    }
}