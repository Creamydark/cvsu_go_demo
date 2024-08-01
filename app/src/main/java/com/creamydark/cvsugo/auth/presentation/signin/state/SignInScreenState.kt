package com.creamydark.cvsugo.auth.presentation.signin.state

data class SignInScreenState(
    val studentID: String = "cvSUHenyo",
    val password: String = "MINJU2024",
    val isLoading: Boolean = false,
    val readOnly:Boolean = true,
    val error: String? = null
)
