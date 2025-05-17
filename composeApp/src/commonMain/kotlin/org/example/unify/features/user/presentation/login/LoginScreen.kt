package org.example.unify.features.user.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.unify.features.user.presentation.components.AuthTextField
import org.example.unify.features.user.presentation.components.ClickableText
import org.example.unify.features.user.presentation.components.CredentialsHeader
import org.example.unify.features.user.presentation.components.PasswordTextField
import org.example.unify.features.user.presentation.components.ProgressiveButton
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import unify.composeapp.generated.resources.Res
import unify.composeapp.generated.resources.email
import unify.composeapp.generated.resources.login
import unify.composeapp.generated.resources.login_to_your_account
import unify.composeapp.generated.resources.no_account
import unify.composeapp.generated.resources.welcome_back


@Composable
fun LoginRoute(
    viewModel: LoginViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LoginScreen(state = state, onEvent = viewModel::handleEvent)
}


@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focus = LocalFocusManager.current
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState()).fillMaxSize().padding(16.dp),
    ) {
        CredentialsHeader(
            title = Res.string.welcome_back,
            body = Res.string.login_to_your_account,
        )
        AuthTextField(
            value = state.email,
            label = stringResource(Res.string.email),
            error = state.emailError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            ),
            onValueChange = { onEvent(LoginEvent.EmailChanged(it)) },
            modifier = Modifier.sizeIn(maxWidth = 600.dp).fillMaxWidth()
        )

        PasswordTextField(
            value = state.password,
            error = state.passwordError,
            isVisible = state.isPasswordVisible,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            onValueChange = { onEvent(LoginEvent.PasswordChanged(it)) },
            onVisibilityToggle = { onEvent(LoginEvent.TogglePasswordVisibility) },
            onDone = {
                focus.clearFocus()
                onEvent(LoginEvent.Login)
            },
            modifier = Modifier.sizeIn(maxWidth = 600.dp).fillMaxWidth(),
            supportingText = if (state.passwordError != null) {
                {
                    Text(
                        text = stringResource(state.passwordError),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            } else {
                null
            },
        )

        ProgressiveButton(
            isLoading = state.loading,
            text = stringResource(Res.string.login),
            onClick = {
                focus.clearFocus()
                onEvent(LoginEvent.Login)
            },
            modifier = Modifier.sizeIn(maxWidth = 600.dp).fillMaxWidth(),
        )

        ClickableText(
            onClick = { onEvent(LoginEvent.NavigateToRegister) },
            content = {
                Text(
                    stringResource(Res.string.no_account),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
        )
    }
}

