package com.kavrin.marvin.domain.use_cases.splash_welcome

import com.kavrin.marvin.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnBoarding(
	private val repository: Repository
) {

	operator fun invoke(): Flow<Boolean> {
		return repository.readOnBoardingState()
	}
}