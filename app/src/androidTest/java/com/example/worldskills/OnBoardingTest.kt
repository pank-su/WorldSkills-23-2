package com.example.worldskills

import android.view.FrameMetrics.ANIMATION_DURATION
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.swipeLeft
import androidx.navigation.compose.rememberNavController
import com.example.worldskills.model.Page
import com.example.worldskills.ui.theme.WorldSkillsTheme
import com.example.worldskills.views.OnBoardingScreen
import org.junit.Rule
import org.junit.Test

class OnBoardingTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun simpleTest() {
        val myPages = listOf(
            Page("1", "1_desc", R.drawable.onboardone),
            Page("2", "2_desc", R.drawable.onboardone)
        )
        composeTestRule.setContent {
            WorldSkillsTheme {
                val fakeNavController = rememberNavController()
                OnBoardingScreen(fakeNavController, myPages)
            }
        }
        composeTestRule.onNodeWithContentDescription("skip_button").assert(hasText("Пропустить"))
        composeTestRule.onNodeWithContentDescription("pager").performTouchInput { swipeLeft() }
        composeTestRule.mainClock.advanceTimeBy(ANIMATION_DURATION.toLong() + 5L)
        composeTestRule.onNodeWithText("1").assertIsNotDisplayed()
        composeTestRule.onNodeWithText("1_desc").assertIsNotDisplayed()
        composeTestRule.onNodeWithText("2").assertIsDisplayed()
        composeTestRule.onNodeWithText("2_desc").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("skip_button").assert(hasText("Завершить"))


    }

}