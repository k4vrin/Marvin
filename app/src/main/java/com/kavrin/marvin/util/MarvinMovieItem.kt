package com.kavrin.marvin.util

import androidx.compose.runtime.Stable
import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.tv.entities.Tv

@Stable
interface MarvinMovieItem {
	val movie: Movie
}

@Stable
interface MarvinTvItem {
	val tv: Tv
}