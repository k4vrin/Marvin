package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.*

@Composable
fun SeeAllCard(
    onCardClicked: () -> Unit
) {

    Card(
        modifier = Modifier
            .height(MAIN_CARD_HEIGHT)
            .width(MAIN_CARD_WIDTH)
            .shadow(
                shape = RoundedCornerShape(size = MEDIUM_PADDING),
                elevation = 2.dp
            ),
        shape = RoundedCornerShape(size = MEDIUM_PADDING),
        backgroundColor = MaterialTheme.colors.cardColor,
        onClick = onCardClicked
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {


            Column {
                IconButton(
                    onClick = onCardClicked
                ) {
                    Icon(
                        modifier = Modifier
                            .size(FAB_SIZE),
                        imageVector = Icons.Outlined.ArrowCircleRight,
                        contentDescription = "See All Icon",
                        tint = MaterialTheme.colors.cardContentColor
                    )
                }

                Text(
                    text = stringResource(R.string.see_all),
                    fontFamily = nunitoTypeFace,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.cardContentColor
                )
            }
        }

    }

}