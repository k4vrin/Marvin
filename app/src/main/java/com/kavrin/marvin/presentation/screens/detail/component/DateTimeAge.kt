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
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.fonts
import com.kavrin.marvin.util.dateFormatter

@Composable
fun DateTimeAge(
    date: String,
    adult: Boolean?,
    modifier: Modifier = Modifier
) {

        Row(
            modifier = modifier
        ) {

            Text(
                text = dateFormatter(date = date),
                fontFamily = fonts,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.cardContentColor
            )

            if (adult != null) {
                Spacer(modifier = Modifier.width(24.dp))

                Text(
                    text = "|",
                    fontFamily = fonts,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.cardContentColor
                )

                Spacer(modifier = Modifier.width(24.dp))

                Text(
                    text = if (adult) "R" else "PG-13",
                    fontFamily = fonts,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.cardContentColor
                )
            }
        }


}


@Preview
@Composable
fun DateTimeAgePreview() {
    DateTimeAge(date = "2022-03-11", adult = false)
}