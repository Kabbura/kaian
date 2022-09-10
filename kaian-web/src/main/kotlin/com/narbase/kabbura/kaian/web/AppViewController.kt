package com.narbase.kabbura.kaian.web

import com.narbase.kunafa.core.lifecycle.Observable
import com.narbase.kabbura.kaian.web.login.LoginPageNavigator
import com.narbase.kabbura.kaian.web.storage.StorageManager
import com.narbase.kabbura.kaian.web.views.basePage.HomePageNavigator


class AppViewController : LoginPageNavigator,
    HomePageNavigator {

    companion object {
        val loginState = Observable<Boolean>()
    }

    override fun onLoggedInSuccessful() {
        StorageManager.setUserLoggedIn(true)
        loginState.value = true
    }

    override fun onLogoutSelected() {
        StorageManager.setUserLoggedIn(false)
        loginState.value = false
    }

    fun onViewCreated() {
        loginState.value = StorageManager.isUserLoggedIn()
    }

}
