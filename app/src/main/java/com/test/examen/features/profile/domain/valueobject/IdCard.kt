package com.test.examen.features.profile.domain.valueobject

private val idRegex = Regex("^[A-Za-z0-9-]{5,20}$")

data class IdCard(val value: String) {
    init {
        require(idRegex.matches(value))
    }
}
