@file:OptIn(ExperimentalCoroutinesApi::class)

package org.example.unify.features.user.presentation.login

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import assertk.assertions.isTrue
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
            assertThat(initialState.email).isEqualTo("")
            assertThat(initialState.emailError).isNull()
            assertThat(initialState.password).isEqualTo("")
            assertThat(initialState.passwordError).isNull()
            assertThat(initialState.isPasswordVisible).isFalse()
            assertThat(initialState.loading).isFalse()
        }
    }

    @Test
    fun `EmailChanged event updates email state`() =
        runTest {
            val testEmail = "test@example.com"

            viewModel.state.test {
                assertThat(awaitItem().email).isEqualTo("")

                viewModel.handleEvent(LoginEvent.EmailChanged(testEmail))

                assertThat(awaitItem().email).isEqualTo(testEmail)
            }
        }

    @Test
    fun `PasswordChanged event updates password state`() =
        runTest {
            val testPassword = "password123"

            viewModel.state.test {
                assertThat(awaitItem().password).isEqualTo("")

                viewModel.handleEvent(LoginEvent.PasswordChanged(testPassword))

                assertThat(awaitItem().password).isEqualTo(testPassword)
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

                assertThat(awaitItem().loading, "Loading before login attempt").isFalse()
                viewModel.handleEvent(LoginEvent.Login)
                assertThat(awaitItem().loading, "Loading after login attempt").isFalse()

                assertThat(loginLambdaCallCount, "Login lambda call count").isEqualTo(1)

                assertThat(
                    navigator.lastDestination(),
                    "Navigation after successful login. Navigations: ${navigator.navigatedToDestinations}"
                ).isEqualTo(Destination.Main)

                assertThat(
                    snackbarManager.shownErrorSnackbarList,
                    "Error snackbar list on success"
                ).isEmpty()
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

            assertThat(viewModel.state.value.loading, "Loading after failed login").isFalse()
            assertThat(navigator.navigatedToDestinations, "Navigations on failure").isEmpty()

            assertThat(
                snackbarManager.shownErrorSnackbarList.size,
                "Snackbar count on failure"
            ).isEqualTo(1)
            assertThat(
                snackbarManager.shownErrorSnackbarList.first().message,
                "Snackbar message on failure"
            ).isEqualTo(errorMessage)
            assertThat(loginLambdaCallCount, "Login lambda call count on failure").isEqualTo(1)
        }

    @Test
    fun `NavigateToRegister event calls navigator`() = runTest {
        viewModel.handleEvent(LoginEvent.NavigateToRegister)

        assertThat(navigator.lastDestination()).isEqualTo(Destination.Auth.Register)
    }

    @Test
    fun `TogglePasswordVisibility event updates isPasswordVisible state`() =
        runTest {

            viewModel.state.test {
                assertThat(awaitItem().isPasswordVisible, "Initial password visibility").isFalse()

                viewModel.handleEvent(LoginEvent.TogglePasswordVisibility)
                assertThat(
                    awaitItem().isPasswordVisible,
                    "Password visibility after first toggle"
                ).isTrue()

                viewModel.handleEvent(LoginEvent.TogglePasswordVisibility)
                assertThat(
                    awaitItem().isPasswordVisible,
                    "Password visibility after second toggle"
                ).isFalse()
            }
        }
}