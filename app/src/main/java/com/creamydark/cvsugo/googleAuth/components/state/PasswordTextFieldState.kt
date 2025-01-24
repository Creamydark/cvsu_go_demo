package com.creamydark.cvsugo.googleAuth.components.state


data class PasswordTextFieldState(
    val text: String = "",
    val isPasswordVisible: Boolean = false,
    val errorMessage: String? = null,
    val readOnly: Boolean = false,
)
