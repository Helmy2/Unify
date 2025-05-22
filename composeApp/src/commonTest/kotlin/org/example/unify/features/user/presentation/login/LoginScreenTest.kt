@file:OptIn(ExperimentalTestApi::class)

package org.example.unify.features.user.presentation.login

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlinx.coroutines.test.runTest
import org.jetbrains.compose.resources.getString
import unify.composeapp.generated.resources.Res
import unify.composeapp.generated.resources.email
import unify.composeapp.generated.resources.error_invalid_email
import unify.composeapp.generated.resources.error_invalid_password
import unify.composeapp.generated.resources.login
import unify.composeapp.generated.resources.login_to_your_account
import unify.composeapp.generated.resources.no_account
import unify.composeapp.generated.resources.password
import unify.composeapp.generated.resources.show_password
import unify.composeapp.generated.resources.welcome_back
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LoginScreenTest {

    private var lastEvent: LoginEvent? = null
    private fun resetLastEvent() {
        lastEvent = null
    }

    private val onEventCollector: (LoginEvent) -> Unit = { lastEvent = it }

    @Test
    fun initialState_displaysCorrectElements() = runComposeUiTest {
        runTest {
            val initialState = LoginState()
            resetLastEvent()

            setContent { LoginScreen(state = initialState, onEvent = onEventCollector) }

            onNodeWithText(getString(Res.string.welcome_back)).assertIsDisplayed()
            onNodeWithText(getString(Res.string.login_to_your_account)).assertIsDisplayed()
            onNodeWithText( getString(Res.string.email)).assertIsDisplayed()
            onNodeWithText(getString(Res.string.password)).assertIsDisplayed()


            onNodeWithText(getString(Res.string.login))
                .assertIsDisplayed()
                .assertIsEnabled()

            onNodeWithText(getString(Res.string.no_account))
                .assertIsDisplayed()
                .assertHasClickAction()
        }
    }

    @Test
    fun emailInput_triggersEmailChangedEvent() = runComposeUiTest {
        runTest {
            val initialState = LoginState()
            resetLastEvent()
            val emailPlaceholder = getString(Res.string.email)

            setContent { LoginScreen(state = initialState, onEvent = onEventCollector) }

            val testEmail = "test@example.com"
            onNodeWithText(emailPlaceholder).performTextInput(testEmail)

            val capturedEvent = lastEvent
            assertNotNull(capturedEvent, "Event should have been captured")
            assertIs<LoginEvent.EmailChanged>(capturedEvent, "Event should be EmailChanged")
            assertEquals(testEmail, capturedEvent.email)
        }
    }

    @Test
    fun passwordInput_triggersPasswordChangedEvent() = runComposeUiTest {
        runTest {
            val initialState = LoginState()
            resetLastEvent()
            val passwordPlaceholder = getString(Res.string.password)

            setContent {
                LoginScreen(state = initialState, onEvent = onEventCollector)
            }

            val testPassword = "password123"
            onNodeWithText(passwordPlaceholder).performTextInput(testPassword)

            val capturedEvent = lastEvent
            assertNotNull(capturedEvent, "Event should have been captured")
            assertIs<LoginEvent.PasswordChanged>(capturedEvent, "Event should be PasswordChanged")
            assertEquals(testPassword, capturedEvent.password)
        }
    }

    @Test
    fun loginButton_click_triggersLoginEvent() = runComposeUiTest {
        runTest {
            val initialState = LoginState(email = "test@example.com", password = "password")
            resetLastEvent()
            val loginButtonText = getString(Res.string.login)

            setContent {
                LoginScreen(state = initialState, onEvent = onEventCollector)
            }

            onNodeWithText(loginButtonText).performClick()

            val capturedEvent = lastEvent
            assertNotNull(capturedEvent, "Event should have been captured")
            assertIs<LoginEvent.Login>(capturedEvent, "Event should be Login")
        }
    }

    @Test
    fun navigateToRegisterLink_click_triggersNavigateToRegisterEvent() = runComposeUiTest {
        runTest {
            val initialState = LoginState()
            resetLastEvent()

            setContent {
                LoginScreen(state = initialState, onEvent = onEventCollector)
            }

            onNodeWithText(getString(Res.string.no_account))
                .assertHasClickAction()
                .performClick()

            val capturedEvent = lastEvent
            assertNotNull(capturedEvent, "Event should have been captured")
            assertIs<LoginEvent.NavigateToRegister>(
                capturedEvent,
                "Event should be NavigateToRegister"
            )
        }
    }

    @Test
    fun emailError_isDisplayed() = runComposeUiTest {
        runTest {
            val errorResource = Res.string.error_invalid_email
            val errorMessage = getString(errorResource)
            val errorState =
                LoginState(emailError = errorResource)
            resetLastEvent()

            setContent {
                LoginScreen(state = errorState, onEvent = onEventCollector)
            }

            onNodeWithText(errorMessage).assertIsDisplayed()
         }
    }

    @Test
    fun passwordError_isDisplayed() = runComposeUiTest {
        runTest {
            val errorResource = Res.string.error_invalid_password
            val errorMessage = getString(errorResource)
            val errorState = LoginState(passwordError = errorResource)
            resetLastEvent()

            setContent {
                LoginScreen(state = errorState, onEvent = onEventCollector)
            }

            onNodeWithText(errorMessage).assertIsDisplayed()
        }
    }

    @Test
    fun passwordVisibilityToggle_triggersToggleEvent_andChangesIcon() = runComposeUiTest {
        runTest {
            var state = LoginState(isPasswordVisible = false)
            resetLastEvent()

            setContent {
                LoginScreen(
                    state = state,
                    onEvent = { event ->
                        lastEvent = event
                        if (event is LoginEvent.TogglePasswordVisibility) {
                            state = state.copy(isPasswordVisible = !state.isPasswordVisible)
                        }
                    }
                )
            }

            val showPasswordDesc = getString(Res.string.show_password)

            onNodeWithContentDescription(showPasswordDesc)
                .assertIsDisplayed()
                .performClick()

            var capturedEvent = lastEvent
            assertNotNull(capturedEvent, "Event should have been captured after first click")
            assertIs<LoginEvent.TogglePasswordVisibility>(capturedEvent, "Event should be TogglePasswordVisibility")
            assertTrue { state.isPasswordVisible }
        }
    }
}


