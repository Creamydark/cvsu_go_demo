package com.creamydark.cvsugo.auth.util

sealed class SignInResult{
    data class Success(val message: String): SignInResult()
    data class Error(val exception: Exception): SignInResult()
}