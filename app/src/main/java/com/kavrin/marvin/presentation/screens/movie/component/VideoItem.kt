package com.kavrin.marvin.presentation.screens.movie.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.ui.theme.MEDIUM_PADDING
import com.kavrin.marvin.ui.theme.cardContentColor
import com.kavrin.marvin.ui.theme.cardGradientColor
import com.kavrin.marvin.ui.theme.nunitoTypeFace

@Composable
fun VideoItem(
    name: String,
    key: String,
    onCardClicked: (key: String) -> Unit
) {

    Card(
        modifier = Modifier
            .height(80.dp)
            .wrapContentWidth(),
        onClick = {
            onCardClicked(key)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth()
                .background(brush = MaterialTheme.colors.cardGradientColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .padding(horizontal = MEDIUM_PADDING),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
            ) {

                Text(
                    modifier = Modifier
                        .requiredWidthIn(min = 60.dp, max = 250.dp)
                        .padding(vertical = MEDIUM_PADDING),
                    text = name,
                    fontFamily = nunitoTypeFace,
                    fontWeight = FontWeight.Normal,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    color = MaterialTheme.colors.cardContentColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                IconButton(
                    modifier = Modifier
                        .requiredSize(48.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.25f),
                            shape = CircleShape
                        ),
                    onClick = {
                        onCardClicked(key)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

            }

        }

    }


}


@Preview
@Composable
fun VideoPreview() {
    VideoItem(
        name = "The Shawshank Redemption(1994) original theatrical trailer",
        key = "vYsQ5mu5Xow"
    ) {}
}