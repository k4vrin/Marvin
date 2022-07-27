package com.kavrin.marvin.presentation.screens.search


sealed class SearchType {
    object MovieType : SearchType()

    object TvType : SearchType()

    object PersonType : SearchType()
}
