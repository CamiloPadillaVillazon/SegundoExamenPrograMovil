package com.test.examen.features.profile.domain.valueobject

private val phoneRegex = Regex("^\\+?[0-9 ]{7,15}$")

data class PhoneNumber(val value: String) {
    init {
        require(phoneRegex.matches(value))
    }
}
