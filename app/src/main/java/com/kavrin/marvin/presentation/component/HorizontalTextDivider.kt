package com.kavrin.marvin.presentation.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace

@Composable
fun HorizontalTextDivider(
    modifier: Modifier = Modifier,
    symbol: String = "●",
    fontSize: TextUnit = MaterialTheme.typography.subtitle2.fontSize,
    fontWeight: FontWeight = FontWeight.Black,
    color: Color = MaterialTheme.colors.cardContentColor
) {
    Text(
        modifier = modifier,
        text = symbol,
        fontFamily = nunitoTypeFace,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color
    )
}