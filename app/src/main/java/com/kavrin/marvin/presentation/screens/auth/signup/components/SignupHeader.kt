package com.kavrin.marvin.presentation.screens.auth.signup.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace

@Composable
fun SignupHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Signup",
            color = MaterialTheme.colors.cardContentColor,
            fontFamily = nunitoTypeFace,
            fontSize = MaterialTheme.typography.h2.fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}