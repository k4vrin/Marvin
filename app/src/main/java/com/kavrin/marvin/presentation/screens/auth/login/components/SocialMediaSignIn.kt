package com.kavrin.marvin.presentation.screens.auth.login.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.R
import com.kavrin.marvin.ui.theme.SMALL_PADDING

@Composable
fun SocialMediaSignIn(
    focusManager: FocusManager,
    onIconClicked: (socialMedia: SocialMedia) -> Unit
) {

    val socialMedias = listOf(
        SocialMedia.Google,
        SocialMedia.Twitter,
        SocialMedia.Facebook
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        socialMedias.forEach { socialMedia ->

            OutlinedButton(
                modifier = Modifier
                    .size(50.dp),
                onClick = { focusManager.clearFocus();onIconClicked(socialMedia) }
            ) {

                Image(
                    modifier = Modifier
                        .size(48.dp),
                    painter = painterResource(id = socialMedia.icon),
                    contentDescription = "Social Media Icon"
                )

            }

            if (socialMedia != socialMedias.last()) Spacer(modifier = Modifier.width(SMALL_PADDING))

        }

    }
}


@Preview(showBackground = true)
@Composable
fun SocialMediaSignInPreview() {
    SocialMediaSignIn(focusManager = LocalFocusManager.current) {}
}

sealed class SocialMedia(@DrawableRes val icon: Int) {
    object Google : SocialMedia(R.drawable.ic_google)
    object Twitter : SocialMedia(R.drawable.ic_facebook)
    object Facebook : SocialMedia(R.drawable.ic_twitter)
}