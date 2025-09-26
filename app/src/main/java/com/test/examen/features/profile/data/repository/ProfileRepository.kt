package com.test.examen.features.profile.data.repository

import com.test.examen.features.profile.domain.model.ProfileModel
import com.test.examen.features.profile.domain.repository.IProfileRepository

class ProfileRepository : IProfileRepository {
    override suspend fun getProfile(): ProfileModel {
        return ProfileModel(
            fullName = "Camilo Padilla",
            age = 23,
            idCard = "CB 42542354",
            gender = "Male",
            phoneNumber = "+591 685345345",
            university = "Universidad Catolica Boliviana",
            maritalStatus = "Married"
        )
    }
}
