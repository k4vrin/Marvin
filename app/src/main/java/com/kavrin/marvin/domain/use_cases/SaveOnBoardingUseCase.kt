package com.kavrin.marvin.domain.use_cases

import com.kavrin.marvin.data.repository.Repository

class SaveOnBoardingUseCase(
	private val repository: Repository
) {

	suspend operator fun invoke(completed: Boolean) {
		repository.saveOnBoardingState(completed = completed)
	}
}