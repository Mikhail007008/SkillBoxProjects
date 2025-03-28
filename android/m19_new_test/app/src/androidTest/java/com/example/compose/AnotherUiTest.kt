package com.example.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.compose.data.model.Character
import com.example.compose.data.model.CharacterLocation
import com.example.compose.ui.viewModel.CharacterViewModel
import com.example.compose.ui.viewModel.LocationViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class CharactersListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @RelaxedMockK
    lateinit var characterViewModel: CharacterViewModel

    @RelaxedMockK
    lateinit var locationViewModel: LocationViewModel

    @Before
    fun setup() {
        val fakeCharacters = listOf(
            Character(id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                gender = "Male",
                location = CharacterLocation("Earth", ""),
                image = "",
                episode = listOf("https://rickandmortyapi.com/api/episode/1")),

            Character(id = 2,
                name = "Morty Smith",
                status = "Alive",
                species = "Human",
                gender = "Male",
                location = CharacterLocation("Earth", ""),
                image = "",
                episode = listOf("https://rickandmortyapi.com/api/episode/1"))
        )

        MockKAnnotations.init(this)
        every { characterViewModel.characters } returns flow {
            emit(PagingData.empty())
            emit(PagingData.from(fakeCharacters))
        }
    }

    @Test
    fun testCharacterListPagination() {
        composeTestRule.setContent {
            RickAndMortyApp(
                characterViewModel = characterViewModel,
                locationViewModel = locationViewModel
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("loadingIndicator").assertIsDisplayed()
    }
}
