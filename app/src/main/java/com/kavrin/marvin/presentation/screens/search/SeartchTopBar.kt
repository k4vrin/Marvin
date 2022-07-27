package com.kavrin.marvin.presentation.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kavrin.marvin.ui.theme.*

@Composable
fun SearchTopBar(
    text: String,
    radioButtonState: RadioButtonState,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String, SearchType) -> Unit,
    onCloseClicked: () -> Unit,
    onTypeClicked: (SearchType) -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topBarBgColor
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = onTextChange,
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search",
                    color = MaterialTheme.colors.topBarContentColor
                )
            },
            textStyle = TextStyle(
                fontFamily = nunitoTypeFace,
                color = MaterialTheme.colors.topBarContentColor
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {},
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colors.topBarContentColor
                    )
                }
            },
            trailingIcon = {
                Row {
                    IconButton(
                        onClick = {
                            if (text.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked()
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Close Icon",
                            tint = MaterialTheme.colors.topBarContentColor
                        )
                    }

                    Spacer(modifier = Modifier.width(EXTRA_SMALL_PADDING))

                    TypeAction(
                        radioButtonState = radioButtonState,
                        onTypeClicked = {
                            onTypeClicked(it)
                        }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text, SearchType.MovieType)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.topBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )

        )

    }

}

@Composable
fun TypeAction(
    radioButtonState: RadioButtonState,
    onTypeClicked: (SearchType) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    IconButton(
        onClick = { expanded = true },
    ) {
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "Select Type",
            tint = MaterialTheme.colors.topBarContentColor
        )
        DropdownMenu(
            modifier = Modifier
                .width(144.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            DropdownMenuItem(
                onClick = {
                    onTypeClicked(SearchType.MovieType)
                }
            ) {
                SearchTypeItem(
                    searchType = SearchType.MovieType,
                    radioButtonState = radioButtonState,
                    onRadioButtonClicked = {
                        onTypeClicked(SearchType.MovieType)
                    }
                )
            }

            DropdownMenuItem(
                onClick = {
                    onTypeClicked(SearchType.TvType)
                }
            ) {
                SearchTypeItem(
                    searchType = SearchType.TvType,
                    radioButtonState = radioButtonState,
                    onRadioButtonClicked = {
                        onTypeClicked(SearchType.TvType)
                    }
                )
            }

            DropdownMenuItem(
                onClick = {
                    onTypeClicked(SearchType.PersonType)
                }
            ) {
                SearchTypeItem(
                    searchType = SearchType.PersonType,
                    radioButtonState = radioButtonState,
                    onRadioButtonClicked = {
                        onTypeClicked(SearchType.PersonType)
                    }
                )
            }
        }
    }

}

@Composable
fun SearchTypeItem(
    searchType: SearchType,
    radioButtonState: RadioButtonState,
    onRadioButtonClicked: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        val selected = remember {
            when (searchType) {
                is SearchType.MovieType -> radioButtonState.movieType
                is SearchType.TvType -> radioButtonState.tvType
                is SearchType.PersonType -> radioButtonState.personType
            }
        }

        RadioButton(
            selected = selected,
            onClick = onRadioButtonClicked,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.fabBgColor,
                unselectedColor = MaterialTheme.colors.topBarContentColor
            )
        )

        val text = remember {
            when (searchType) {
                is SearchType.MovieType -> "Movie"
                is SearchType.TvType -> "Tv"
                is SearchType.PersonType -> "Person"
            }
        }

        Text(
            text = text,
            fontFamily = nunitoTypeFace,
            fontWeight = FontWeight.SemiBold,
            fontSize = MaterialTheme.typography.h6.fontSize
        )

    }

}