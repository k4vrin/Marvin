package com.kavrin.marvin.util

import com.kavrin.marvin.domain.model.movie.entities.Movie
import com.kavrin.marvin.domain.model.tv.entities.Tv

interface MarvinItem {
	val movie: Movie? get() = null
	val tv: Tv? get() = null
}