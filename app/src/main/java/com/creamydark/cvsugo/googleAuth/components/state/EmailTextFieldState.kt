package com.creamydark.cvsugo.googleAuth.components.state

data class EmailTextFieldState(
    val text: String = "",
    val errorMessage: String? = null,
    val readOnly: Boolean = false
)