package com.kavrin.marvin.presentation.screens.person.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.kavrin.marvin.domain.use_cases.person.PersonConstants
import com.kavrin.marvin.presentation.component.HorizontalTextDivider
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace

@Composable
fun PersonInfo(
    personInfo: Map<String, Int?>,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        personInfo[PersonConstants.AGE]?.let {
            Text(
                text = customString(
                    first = it.toString(),
                    last = " years old"
                )
            )
        }

        personInfo[PersonConstants.TOTAL_MOVIES]?.let {

            HorizontalTextDivider()

            Text(
                text = customString(
                    first = it.toString(),
                    last = " movies"
                )
            )
        }

        personInfo[PersonConstants.TOTAL_TVS]?.let {

            HorizontalTextDivider()

            Text(
                text = customString(
                    first = it.toString(),
                    last = " tv shows"
                )
            )
        }

    }

}

@Composable
private fun customString(
    first: String,
    last: String
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colors.cardContentColor
            )
        ) {
            append(text = first)
        }

        withStyle(
            style = SpanStyle(
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colors.cardContentColor
            )
        ) {
            append(text = last)
        }
    }
}