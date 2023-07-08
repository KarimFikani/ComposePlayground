package com.example.composeplayground

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.composeplayground.ui.theme.ComposePlaygroundTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    ComposePlaygroundTheme {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Sample"
                        )
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            snackbarHost = {},
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) {
            var password by rememberSaveable { mutableStateOf("") }
            var passwordHidden by rememberSaveable { mutableStateOf(true) }

            val shouldEnableButton by remember {
                derivedStateOf {
                    password.length >= 8
                }
            }

            Column {
                PasswordField(
                    password = password,
                    passwordHidden = passwordHidden,
                    paddingValues = it,
                    onValueChange = { value -> password = value },
                    onTrailingIconClick = { passwordHidden = !passwordHidden }
                )

                Button(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary
                            .copy(alpha = 0.4F),
                        disabledContentColor = MaterialTheme.colorScheme.onPrimary
                            .copy(alpha = 0.4F),
                    ),
                    enabled = shouldEnableButton,
                    shape = RoundedCornerShape(6.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "Enter",
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun PasswordField(
    password: String,
    passwordHidden: Boolean,
    paddingValues: PaddingValues,
    onValueChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit
) {
    TextField(
        modifier = Modifier.padding(paddingValues),
        value = password,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text("Enter password") },
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = onTrailingIconClick) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Default.Home else Icons.Filled.CheckCircle
                // Please provide localized description for accessibility services
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        }
    )
}
