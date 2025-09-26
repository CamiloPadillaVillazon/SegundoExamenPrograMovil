package com.test.examen.features.profile.domain.valueobject

data class Age(val value: Int) {
    init {
        require(value in 0..120)
    }
}
