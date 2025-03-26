package com.example.compose

import com.example.compose.data.model.Character
import com.example.compose.data.model.CharacterLocation
import com.example.compose.data.model.Episode
import com.example.compose.data.repository.Repository
import com.example.compose.ui.viewModel.CharacterViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterViewModelTest {
    @get:Rule
    val testDispatcherRule = TestCoroutineRule()

    private lateinit var viewModel: CharacterViewModel
    private val repository: Repository = mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel = CharacterViewModel(repository)
    }

    @Test
    fun `getCharacters should call repository getCharacters`() = runTest {
        repository.getCharacters()

        verify { repository.getCharacters() }
    }

    @Test
    fun `getCharacterById should update characterDetail state`() = runTest {
        val character = Character(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            location = CharacterLocation(name = "Earth", url = "https://rickandmortyapi.com/api/location/1"),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1")
        )

        coEvery { repository.getCharacterById(1) } returns character

        viewModel.getCharacterById(1)
        advanceUntilIdle()

        assertEquals(character, viewModel.characterDetail.value)
        coVerify { repository.getCharacterById(1) }
    }

    @Test
    fun `fetchFirstEpisode should update firstEpisode state`() = runTest {
        val episode = Episode(
            id = 1,
            name = "Pilot",
            air_date = "December 2, 2013",
            episode = "S01E01"
        )
        val character = Character(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            location = CharacterLocation(name = "Earth", url = "https://rickandmortyapi.com/api/location/1"),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1")
        )

        coEvery { repository.getFirstEpisode("https://rickandmortyapi.com/api/episode/1") } returns episode

        viewModel.fetchFirstEpisode(character)
        advanceUntilIdle()

        assertEquals(episode, viewModel.firstEpisode.value)
    }

    @Test
    fun `getEpisodeById should use cache after first load`() = runTest {
        val episodeUrl = "https://rickandmortyapi.com/api/episode/1"
        val episode = Episode(
            id = 1,
            name = "Pilot",
            air_date = "December 2, 2013",
            episode = "S01E01"
        )
        coEvery { repository.getFirstEpisode(episodeUrl) } returns episode

        val firstFlow = viewModel.getEpisodeById(1)
        advanceUntilIdle()
        val firstValue = firstFlow.value

        coVerify(exactly = 1) { repository.getFirstEpisode(episodeUrl) }
        assertEquals(episode, firstValue)
    }

    @Test
    fun `getCharacterById should handle errors and update characterDetail state with null`() = runTest {
        coEvery { repository.getCharacterById(1) } throws Exception("Network error")

        viewModel.getCharacterById(1)
        advanceUntilIdle()

        assertNull(viewModel.characterDetail.value)
        coVerify { repository.getCharacterById(1) }
    }
}