package org.example.unify.features.user.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import unify.composeapp.generated.resources.Res
import unify.composeapp.generated.resources.cancel
import unify.composeapp.generated.resources.confirm
import unify.composeapp.generated.resources.name
import unify.composeapp.generated.resources.update_name
import org.jetbrains.compose.resources.stringResource

@Composable
fun UpdateNameDialog(
    name: String,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(modifier) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.update_name),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                AuthTextField(
                    value = name,
                    label = stringResource(Res.string.name),
                    error = null,
                    keyboardOptions = KeyboardOptions.Default,
                    onValueChange = onValueChange,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = onDismiss,
                    ) {
                        Text(text = stringResource(Res.string.cancel))
                    }
                    Button(
                        onClick = onConfirm,
                    ) {
                        Text(text = stringResource(Res.string.confirm))
                    }
                }
            }
        }
    }
}