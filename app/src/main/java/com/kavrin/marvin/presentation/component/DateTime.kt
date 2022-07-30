package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace
import com.kavrin.marvin.util.dateFormatter

@Composable
fun DateTime(
    date: String?,
    time: Int?,
    modifier: Modifier = Modifier,
    status: String? = null
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        if (!date.isNullOrBlank()) {

            val formattedDate = dateFormatter(date = date)
            val year = formattedDate.substringAfterLast(delimiter = " ")
            val rest = formattedDate.substringBeforeLast(delimiter = " ")
            val releaseDate = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.SemiBold,
                    )
                ) {
                    append(text = rest)
                }

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                    )
                ) {
                    append(text = " $year")
                }
            }

            Text(
                text = releaseDate,
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.cardContentColor
            )
        }

        if (!date.isNullOrBlank() && time != null) {
            HorizontalTextDivider()
        }

        if (time != null) {

            val runtime = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                    )
                ) {
                    append(text = "$time")
                }

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.SemiBold,
                    )
                ) {
                    append(text = "min")
                }
            }

            Text(
                text = runtime,
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.cardContentColor
            )
        }

        if (
            (time != null && !status.isNullOrBlank()) ||
            (!date.isNullOrBlank() && !status.isNullOrBlank())
        ) {
            HorizontalTextDivider()
        }

        if (!status.isNullOrBlank()) {
            Text(
                text = status,
                fontFamily = nunitoTypeFace,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.cardContentColor
            )
        }
    }


}


@Preview(showBackground = true)
@Composable
fun DateTimeAgePreview() {
    DateTime(date = "2022-03-11", time = 192, status = "Released")
}