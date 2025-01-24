package com.creamydark.cvsugo.core.util

sealed class UIActions{
    data class NavigateToOfferedDetail(val id:String):UIActions()
    data class Toast(val message:String):UIActions()
}
