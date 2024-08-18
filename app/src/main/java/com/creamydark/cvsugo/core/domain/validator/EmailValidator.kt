package com.creamydark.cvsugo.core.domain.validator


class EmailValidator {
    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@cvsu\\.edu\\.ph$")

    fun isValidEmail(email: String): Boolean {
        return emailRegex.matches(email)
    }
}