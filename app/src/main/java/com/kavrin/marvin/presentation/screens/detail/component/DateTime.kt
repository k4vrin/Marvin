package com.kavrin.marvin.presentation.screens.detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.kavrin.marvin.ui.theme.LARGE_PADDING
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.fonts
import com.kavrin.marvin.util.dateFormatter

@Composable
fun DateTime(
    date: String,
    time: Int,
    modifier: Modifier = Modifier
) {

        Row(
            modifier = modifier
        ) {

            Text(
                text = dateFormatter(date = date),
                fontFamily = fonts,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.cardContentColor
            )

            Spacer(modifier = Modifier.width(LARGE_PADDING))

            Text(
                text = "|",
                fontFamily = fonts,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.cardContentColor
            )

            Spacer(modifier = Modifier.width(LARGE_PADDING))

            Text(
                text = "${time}min",
                fontFamily = fonts,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.cardContentColor
            )
        }


}


@Preview
@Composable
fun DateTimeAgePreview() {
    DateTime(date = "2022-03-11", time = 192)
}