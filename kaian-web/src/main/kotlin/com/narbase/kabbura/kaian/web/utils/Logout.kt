package com.narbase.kabbura.kaian.web.utils

import com.narbase.kabbura.kaian.web.AppViewController
import com.narbase.kabbura.kaian.web.storage.StorageManager

fun logoutUser() {
    StorageManager.accessToken = null
    StorageManager.setUserLoggedIn(false)
    AppViewController.loginState.value = false
}
