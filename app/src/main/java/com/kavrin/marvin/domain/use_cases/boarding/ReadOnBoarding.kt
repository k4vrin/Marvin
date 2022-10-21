package com.kavrin.marvin.domain.use_cases.boarding

import com.kavrin.marvin.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadOnBoarding(
	private val repository: Repository
) {

	operator fun invoke(): Flow<Boolean> {
		return repository.readOnBoardingState()
	}
}