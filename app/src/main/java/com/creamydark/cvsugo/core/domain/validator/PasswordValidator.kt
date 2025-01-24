package com.creamydark.cvsugo.core.domain.validator


class PasswordValidator {

    private val specialCharacters = "!@#$%&*"

    fun validate(password: String): Boolean {
        return isLengthValid(password)
    }

    private fun isLengthValid(password: String): Boolean {
        return password.length >= 8
    }
}