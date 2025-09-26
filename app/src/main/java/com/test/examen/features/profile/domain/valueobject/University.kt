package com.test.examen.features.profile.domain.valueobject

data class University(val value: String) {
    init {
        require(value.isNotBlank())
        require(value.trim().length in 2..120)
    }
}
