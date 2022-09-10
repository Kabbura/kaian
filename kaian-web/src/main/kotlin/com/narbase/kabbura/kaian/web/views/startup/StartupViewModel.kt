package com.narbase.kabbura.kaian.web.views.startup

import com.narbase.kunafa.core.lifecycle.Observable
import com.narbase.kabbura.kaian.dto.domain.user.profile.GetProfileDto
import com.narbase.kabbura.kaian.dto.models.roles.Privilege
import com.narbase.kabbura.kaian.web.network.ServerCaller
import com.narbase.kabbura.kaian.web.network.basicNetworkCall
import com.narbase.kabbura.kaian.web.storage.CurrentUserProfile
import com.narbase.kabbura.kaian.web.storage.SessionInfo
import com.narbase.kabbura.kaian.web.storage.StorageManager
import com.narbase.kabbura.kaian.web.utils.BasicUiState

/*
 * Copyright 2017-2020 Narbase technologies and contributors. Use of this source code is governed by the MIT License.
 */

class StartupViewModel {
    val getConfigUiState = Observable<BasicUiState>()


    fun getConfig() {
        if (StorageManager.isUserLoggedIn().not()) {
            SessionInfo.currentUser = null
            getConfigUiState.value = BasicUiState.Loaded
            return
        }
        basicNetworkCall(getConfigUiState) {
            val profile = ServerCaller.getUserProfiles().data.profile
            SessionInfo.currentUser = profile.toCurrentUserProfile()
            // You can check app version or subscription here as well
        }
    }

    private fun GetProfileDto.UserProfile.toCurrentUserProfile() =
        CurrentUserProfile(
            clientId,
            userId,
            fullName,
            username,
            callingCode,
            localPhone,
            privileges.map { Privilege.valueOf(it) })
}
