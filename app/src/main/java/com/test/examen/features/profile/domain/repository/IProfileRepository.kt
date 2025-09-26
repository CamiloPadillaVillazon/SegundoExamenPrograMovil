package com.test.examen.features.profile.domain.repository

import com.test.examen.features.profile.domain.model.ProfileModel

interface IProfileRepository {
    suspend fun getProfile(): ProfileModel
}
