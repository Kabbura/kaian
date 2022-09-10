package com.narbase.kabbura.kaian.web.network

import com.narbase.kunafa.core.lifecycle.Observable
import com.narbase.kabbura.kaian.web.utils.BasicUiState
import kotlinx.coroutines.CoroutineScope

/*
 * Copyright 2017-2020 Narbase technologies and contributors. Use of this source code is governed by the MIT License.
 */

fun basicNetworkCall(uiState: Observable<BasicUiState>, call: suspend CoroutineScope.() -> Unit) {
    networkCall(
        before = { uiState.value = BasicUiState.Loading },
        onConnectionError = { uiState.value = BasicUiState.Error }
    ) {
        try {
            call()
            uiState.value = BasicUiState.Loaded
        } catch (t: Throwable) {
            uiState.value = BasicUiState.Error
            t.printStackTrace()
        }
    }
}
