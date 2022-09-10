package com.narbase.kabbura.kaian.domain.utils

import com.narbase.kabbura.kaian.common.auth.loggedin.AuthorizedClientData
import com.narbase.kabbura.kaian.common.exceptions.DisabledUserException
import com.narbase.kabbura.kaian.data.tables.UsersTable
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/*
 * Copyright 2017-2020 Narbase technologies and contributors. Use of this source code is governed by the MIT License.
 */


fun Route.addInactiveUserInterceptor() {
    intercept(ApplicationCallPipeline.Call) {
        val authorizedClientData = call.principal<AuthorizedClientData>()
        val isInactive = transaction {
            authorizedClientData?.id?.let { clientId ->
                UsersTable.select { UsersTable.clientId eq UUID.fromString(clientId) }.firstOrNull()
                    ?.get(UsersTable.isInactive)
            }
        }
        if (isInactive == true)
            throw DisabledUserException()
    }
}