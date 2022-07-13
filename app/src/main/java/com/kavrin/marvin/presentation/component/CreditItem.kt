package com.kavrin.marvin.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.*
import com.kavrin.marvin.util.Constants

@Composable
fun CreditItem(
    name: String,
    character: String,
    creditId: Int,
    image: String?,
    modifier: Modifier = Modifier,
    onItemClicked: (Int) -> Unit
) {

    val painter = rememberAsyncImagePainter(
        model = "${Constants.IMAGE_BASE_URL}${image}",
        placeholder = painterResource(id = R.drawable.placeholder),
        error = painterResource(id = R.drawable.placeholder)
    )

    Card(
        modifier = modifier
            .height(MAIN_CARD_HEIGHT)
            .width(MAIN_CARD_WIDTH)
            .shadow(
                shape = RoundedCornerShape(size = MEDIUM_PADDING),
                elevation = 2.dp
            ),
        shape = RoundedCornerShape(size = MEDIUM_PADDING),
        backgroundColor = MaterialTheme.colors.cardColor,
        onClick = {
            onItemClicked(creditId)
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .weight(7f)
            ) {

                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painter,
                    contentDescription = "Cast Image",
                    contentScale = ContentScale.Crop
                )

            }

            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(all = MEDIUM_PADDING)
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = name,
                    color = MaterialTheme.colors.cardContentColor,
                    fontFamily = nunitoTypeFace,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(SMALL_PADDING))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = character,
                    color = MaterialTheme.colors.cardContentColor,
                    fontFamily = nunitoTypeFace,
                    fontSize = MaterialTheme.typography.body2.fontSize,
                    fontWeight = FontWeight.Normal,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

            }



        }

    }

}

@Preview
@Composable
fun CastItemPreview() {
    CreditItem(
        name = "Brad Pit",
        character = "John Doe",
        image = "",
        creditId = 1,
        onItemClicked = {}
    )
}