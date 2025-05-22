@file:OptIn(ExperimentalCoroutinesApi::class)

package org.example.unify.features.user.presentation.login

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.example.unify.core.domain.navigation.Destination
import org.example.unify.core.fake.FakeNavigator
import org.example.unify.core.fake.FakeSnackbarManager
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue


class LoginViewModelTest {
    private var loginShouldSucceed: Boolean = true
    private var loginException: Throwable? = null
    private val _isUserLoggedInFlow = MutableStateFlow<Result<Boolean>>(Result.success(false))

    private var loginLambdaCallCount = 0

    private lateinit var snackbarManager: FakeSnackbarManager
    private lateinit var navigator: FakeNavigator
    private lateinit var viewModel: LoginViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        snackbarManager = FakeSnackbarManager()
        navigator = FakeNavigator()
        resetLambdaControls()
        initializeViewModel()
    }

    private fun resetLambdaControls() {
        loginShouldSucceed = true
        loginException = null
        _isUserLoggedInFlow.value = Result.success(false)
        loginLambdaCallCount = 0
    }

    private fun initializeViewModel() {
        val mockLoginUseCase: suspend (String, String) -> Result<Unit> = { email, password ->
            loginLambdaCallCount++
            if (loginShouldSucceed) {
                _isUserLoggedInFlow.value = Result.success(true)
                Result.success(Unit)
            } else {
                Result.failure(loginException ?: IllegalStateException("Login failed by mock lambda"))
            }
        }

        val mockIsUserLoggedInFlowUseCase: () -> Flow<Result<Boolean>> = {
            _isUserLoggedInFlow
        }

        viewModel = LoginViewModel(
            loginUseCase = mockLoginUseCase,
            isUserLongedInFlowUseCase = mockIsUserLoggedInFlowUseCase,
            snackbarManager = snackbarManager,
            navigator = navigator
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        snackbarManager.clear()
        navigator.clear()
    }

    @Test
    fun `initial state is correct`() = runTest {
        viewModel.state.test {
            val initialState = awaitItem() // Get the initial state
            assertEquals("", initialState.email)
            assertNull(initialState.emailError)
            assertEquals("", initialState.password)
            assertNull(initialState.passwordError)
            assertFalse(initialState.isPasswordVisible)
            assertFalse(initialState.loading)
        }
    }

    @Test
    fun `EmailChanged event updates email state`() =
        runTest {
            val testEmail = "test@example.com"

            viewModel.state.test {
                assertEquals("", awaitItem().email)

                viewModel.handleEvent(LoginEvent.EmailChanged(testEmail))

                assertEquals(testEmail, awaitItem().email)
            }
        }

    @Test
    fun `PasswordChanged event updates password state`() =
        runTest {
            val testPassword = "password123"

            viewModel.state.test {
                assertEquals("", awaitItem().password)

                viewModel.handleEvent(LoginEvent.PasswordChanged(testPassword))

                assertEquals(testPassword, awaitItem().password)
            }
        }

    @Test
    fun `login success updates loading state and triggers navigation via isUserLongedInFlowUseCase`() =
        runTest {
            val email = "user@example.com"
            val password = "password"
            loginShouldSucceed = true

            viewModel.state.test {
                viewModel.handleEvent(LoginEvent.EmailChanged(email))
                viewModel.handleEvent(LoginEvent.PasswordChanged(password))

                assertFalse(awaitItem().loading, "Loading before login attempt")
                viewModel.handleEvent(LoginEvent.Login)
                assertFalse(awaitItem().loading, "Loading after login attempt")

                assertEquals(1, loginLambdaCallCount, "Login lambda call count")

                assertEquals(Destination.Main, navigator.lastDestination(), "Navigation after successful login. Navigations: ${navigator.navigatedToDestinations}")

                assertTrue(snackbarManager.shownErrorSnackbarList.isEmpty(), "Error snackbar list on success")
            }
        }

    @Test
    fun `login failure shows snackbar, updates loading state, and does not navigate`() =
        runTest {
            initializeViewModel()
            val email = "user@example.com"
            val password = "wrong"
            val errorMessage = "Invalid credentials from mock"

            loginShouldSucceed = false
            loginException = IllegalStateException(errorMessage)

            viewModel.handleEvent(LoginEvent.EmailChanged(email))
            viewModel.handleEvent(LoginEvent.PasswordChanged(password))
            viewModel.handleEvent(LoginEvent.Login)
            advanceUntilIdle()

            assertFalse(viewModel.state.value.loading, "Loading after failed login")
            assertTrue(navigator.navigatedToDestinations.isEmpty(), "Navigations on failure")

            assertEquals(1, snackbarManager.shownErrorSnackbarList.size, "Snackbar count on failure")
            assertEquals(errorMessage, snackbarManager.shownErrorSnackbarList.first().message, "Snackbar message on failure")
            assertEquals(1, loginLambdaCallCount, "Login lambda call count on failure")
        }

    @Test
    fun `NavigateToRegister event calls navigator`() = runTest {
        viewModel.handleEvent(LoginEvent.NavigateToRegister)

        assertEquals(Destination.Auth.Register, navigator.lastDestination())
    }

    @Test
    fun `TogglePasswordVisibility event updates isPasswordVisible state`() =
        runTest {
            viewModel.state.test {
                assertFalse(awaitItem().isPasswordVisible, "Initial password visibility")
                viewModel.handleEvent(LoginEvent.TogglePasswordVisibility)
                assertTrue(awaitItem().isPasswordVisible, "Password visibility after first toggle")
                viewModel.handleEvent(LoginEvent.TogglePasswordVisibility)
                assertFalse(awaitItem().isPasswordVisible, "Password visibility after second toggle")
            }
        }
}