package com.test.examen.features.profile.domain.usecase

import com.test.examen.features.profile.domain.model.ProfileModel
import com.test.examen.features.profile.domain.repository.IProfileRepository

class GetUserUseCase(
    private val repository: IProfileRepository
) {
    suspend operator fun invoke(): ProfileModel = repository.getProfile()
}
