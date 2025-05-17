package org.example.unify.features.user.presentation.register

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import org.example.unify.features.user.domain.entity.PasswordStrength
import org.example.unify.features.user.presentation.components.AuthTextField
import org.example.unify.features.user.presentation.components.ClickableText
import org.example.unify.features.user.presentation.components.CredentialsHeader
import org.example.unify.features.user.presentation.components.PasswordStrengthIndicator
import org.example.unify.features.user.presentation.components.PasswordTextField
import org.example.unify.features.user.presentation.components.ProgressiveButton
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import unify.composeapp.generated.resources.Res
import unify.composeapp.generated.resources.already_have_account
import unify.composeapp.generated.resources.create_account
import unify.composeapp.generated.resources.email
import unify.composeapp.generated.resources.name
import unify.composeapp.generated.resources.no_account
import unify.composeapp.generated.resources.register

@Composable
fun RegisterRoute(
    viewModel: RegisterViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    RegisterScreen(state = state, onEvent = viewModel::handleEvent)
}

@Composable
fun RegisterScreen(
    state: RegisterState, onEvent: (RegisterEvent) -> Unit, modifier: Modifier = Modifier
) {
    val focus = LocalFocusManager.current

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState()).fillMaxSize().padding(16.dp),
    ) {

        CredentialsHeader(
            title = Res.string.create_account,
            body = Res.string.no_account,
        )

        AuthTextField(
            value = state.name,
            label = stringResource(Res.string.name),
            error = state.nameError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = { onEvent(RegisterEvent.NameChanged(it)) },
            modifier = Modifier.sizeIn(maxWidth = 600.dp).fillMaxWidth()
        )

        AuthTextField(
            value = state.email,
            label = stringResource(Res.string.email),
            error = state.emailError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            ),
            onValueChange = { onEvent(RegisterEvent.EmailChanged(it)) },
            modifier = Modifier.sizeIn(maxWidth = 600.dp).fillMaxWidth()
        )

        PasswordTextField(
            value = state.password,
            error = state.passwordError,
            isVisible = state.isPasswordVisible,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
            ),
            onValueChange = { onEvent(RegisterEvent.PasswordChanged(it)) },
            onVisibilityToggle = { onEvent(RegisterEvent.TogglePasswordVisibility) },
            onDone = {
                focus.clearFocus()
                onEvent(RegisterEvent.Register)
            },
            modifier = Modifier.sizeIn(maxWidth = 600.dp).fillMaxWidth(),
            supportingText = {
                AnimatedContent(state.passwordError != null) {
                    if (it) {
                        Text(
                            text = if (state.passwordError != null) stringResource(state.passwordError) else "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    } else {
                        AnimatedVisibility(
                            state.passwordStrength != PasswordStrength.STRONG && state.password.isNotEmpty()
                        ) {
                            PasswordStrengthIndicator(
                                strength = state.passwordStrength,
                                requirements = state.passwordRequirements,
                            )
                        }
                    }
                }
            })

        ProgressiveButton(
            isLoading = state.isLoading, text = stringResource(Res.string.register), onClick = {
                focus.clearFocus()
                onEvent(RegisterEvent.Register)
            }, modifier = Modifier.sizeIn(maxWidth = 600.dp).fillMaxWidth()
        )


        ClickableText(
            onClick = { onEvent(RegisterEvent.NavigateToLogin) },
            content = {
                Text(
                    stringResource(Res.string.already_have_account),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
        )
    }
}