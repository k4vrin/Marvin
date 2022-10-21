package com.kavrin.marvin.presentation.screens.auth.login.components

import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.fabBgColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace

@Composable
fun Register(
    onRegisterClick: () -> Unit
) {
    val registerDesc = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.cardContentColor
            )
        ) {
            append(text = "Not a member? ")
        }

        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.fabBgColor
            )
        ) {
            append(text = "Register Now")
        }
    }

    Text(
        modifier = Modifier
            .clickable { onRegisterClick() },
        fontFamily = nunitoTypeFace,
        fontSize = MaterialTheme.typography.body2.fontSize,
        fontWeight = FontWeight.SemiBold,
        text = registerDesc,
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    Register {}
}