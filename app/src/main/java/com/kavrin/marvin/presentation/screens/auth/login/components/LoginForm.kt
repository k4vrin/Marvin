package com.kavrin.marvin.presentation.screens.auth.login.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.R
import com.kavrin.marvin.presentation.component.LoadingAnimation
import com.kavrin.marvin.ui.theme.*

@Composable
fun LoginForm(
    hasError: Boolean = false,
    errorMessage: String = "",
    usernameOrEmail: String,
    password: String,
    isProcessing: Boolean = false,
    focusManager: FocusManager,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClicked: (user: String, pass: String) -> Unit,
    onRecoverClicked: () -> Unit
) {

    var showPassword by remember { mutableStateOf(false) }

    Column {

        ///// Username /////
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = usernameOrEmail,
            onValueChange = onUsernameChange,
            label = { marvinTextFieldLabel("Username or email") },
            textStyle = marvinTextFieldTextStyle,
            singleLine = true,
            isError = hasError,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) },
            ),
            colors = marvinTextFieldColors

        )

        Spacer(modifier = Modifier.height(SMALL_PADDING))

        ///// Password /////
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = password,
            onValueChange = onPasswordChange,
            label = { marvinTextFieldLabel("Password") },
            textStyle = marvinTextFieldTextStyle,
            singleLine = true,
            isError = hasError,
            visualTransformation =
            if (showPassword) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                PassTxtFieldIcon(showPassword = showPassword) {
                    showPassword = !showPassword
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() },
            ),
            colors = marvinTextFieldColors

        )

        Spacer(modifier = Modifier.height(SMALL_PADDING))

        ///// Error /////
        AnimatedVisibility(visible = hasError) {
            Text(
                modifier = Modifier
                    .align(Alignment.End),
                text = errorMessage,
                textAlign = TextAlign.End,
                color = RedNCS,
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.body2.fontSize,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Spacer(modifier = Modifier.height(MEDIUM_PADDING))

        ///// Recover /////
        Text(
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onRecoverClicked() },
            text = "Recover Password",
            textAlign = TextAlign.End,
            color = MaterialTheme.colors.fabBgColor,
            fontFamily = nunitoTypeFace,
            fontSize = MaterialTheme.typography.body2.fontSize,
            fontWeight = FontWeight.SemiBold,
        )

        Spacer(modifier = Modifier.height(LARGE_PADDING))

        //// Login Button ////
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
                focusManager.clearFocus()
                onLoginClicked(usernameOrEmail, password)
            },
            enabled = !isProcessing,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.fabBgColor,
                contentColor = MaterialTheme.colors.fabContentColor
            )
        ) {

            AnimatedVisibility(visible = !isProcessing) {
                //// Text In Button ////
                Text(
                    text = stringResource(R.string.login),
                    fontFamily = nunitoTypeFace,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    textAlign = TextAlign.Center
                )
            }

            AnimatedVisibility(visible = isProcessing) {
                LoadingAnimation()
            }

        }

    }


}

@Preview(showBackground = true)
@Composable
fun LoginFormPreview() {
    LoginForm(
        usernameOrEmail = "Kavrin",
        password = "1234",
        hasError = false,
        isProcessing = true,
        errorMessage = "Username or password are not correct",
        onUsernameChange = {},
        onPasswordChange = {},
        onLoginClicked = { user, pass ->  },
        onRecoverClicked = {},
        focusManager = LocalFocusManager.current
    )
}