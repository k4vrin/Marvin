package com.kavrin.marvin.presentation.screens.auth.signup.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.R
import com.kavrin.marvin.presentation.component.LoadingAnimation
import com.kavrin.marvin.ui.theme.*

@Composable
fun SignupForm(
    username: String,
    email: String,
    password: String,
    hasUsernameError: Boolean,
    usernameErrorMessage: String?,
    hasEmailError: Boolean,
    emailErrorMessage: String?,
    hasPasswordError: Boolean,
    passwordErrorMessage: String?,
    isProcessing: Boolean,
    focusManager: FocusManager,
    onSignUpClicked: () -> Unit,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {

    var showPassword by remember { mutableStateOf(false) }

    Column {

        ///// Username /////
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = username,
            onValueChange = onUsernameChange,
            label = { marvinTextFieldLabel("Username or email") },
            textStyle = marvinTextFieldTextStyle,
            singleLine = true,
            isError = hasUsernameError,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) },
            ),
            colors = marvinTextFieldColors

        )

        Spacer(modifier = Modifier.height(MEDIUM_PADDING))

        ///// Username Error /////
        AnimatedVisibility(visible = hasUsernameError) {
            Text(
                modifier = Modifier
                    .align(Alignment.End),
                text = usernameErrorMessage!!,
                textAlign = TextAlign.End,
                color = RedNCS,
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.body2.fontSize,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Spacer(modifier = Modifier.height(SMALL_PADDING))

        ///// Email /////
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = email,
            onValueChange = onEmailChange,
            label = { marvinTextFieldLabel("Username or email") },
            textStyle = marvinTextFieldTextStyle,
            singleLine = true,
            isError = hasEmailError,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) },
            ),
            colors = marvinTextFieldColors

        )

        Spacer(modifier = Modifier.height(MEDIUM_PADDING))

        ///// Email Error /////
        AnimatedVisibility(visible = hasEmailError) {
            Text(
                modifier = Modifier
                    .align(Alignment.End),
                text = emailErrorMessage!!,
                textAlign = TextAlign.End,
                color = RedNCS,
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.body2.fontSize,
                fontWeight = FontWeight.SemiBold,
            )
        }

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
            isError = hasPasswordError,
            visualTransformation =
            if (showPassword) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                PassTxtFieldIcon(showPassword = showPassword) { showPassword = !showPassword }
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

        Spacer(modifier = Modifier.height(MEDIUM_PADDING))

        ///// Password Error /////
        AnimatedVisibility(visible = hasPasswordError) {
            Text(
                modifier = Modifier
                    .align(Alignment.End),
                text = passwordErrorMessage!!,
                textAlign = TextAlign.End,
                color = RedNCS,
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.body2.fontSize,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Spacer(modifier = Modifier.height(LARGE_PADDING))

        //// SignUp Button ////
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
                focusManager.clearFocus()
                onSignUpClicked()
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
                    text = stringResource(R.string.signup),
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