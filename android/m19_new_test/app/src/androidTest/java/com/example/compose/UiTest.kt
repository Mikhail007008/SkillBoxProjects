package com.example.compose

import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.paging.PagingData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.compose.data.model.Character
import com.example.compose.data.model.CharacterLocation
import com.example.compose.data.model.Location
import com.example.compose.ui.screen.CharactersScreen
import com.example.compose.ui.viewModel.CharacterViewModel
import com.example.compose.ui.viewModel.LocationViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule
import kotlinx.coroutines.flow.flowOf
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
@MediumTest
class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCharacterListDisplays() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)

        composeTestRule.onNodeWithText("Rick Sanchez").assertIsDisplayed()
    }

    @Test
    fun testNavigateToCharacterDetailScreen() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Rick Sanchez").performClick()
        composeTestRule.onNodeWithText("Character Details").assertIsDisplayed()
    }
}