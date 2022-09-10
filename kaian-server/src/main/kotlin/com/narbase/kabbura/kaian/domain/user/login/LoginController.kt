package com.narbase.kabbura.kaian.domain.user.login

import com.narbase.kabbura.kaian.common.DataResponse
import com.narbase.kabbura.kaian.common.Handler
import com.narbase.kabbura.kaian.common.auth.loggedin.AuthorizedClientData
import com.narbase.kabbura.kaian.data.access.clients.ClientsDao
import com.narbase.kabbura.kaian.domain.utils.authenticatedClient
import com.narbase.kabbura.kaian.dto.domain.user.login.LoginDto
import org.jetbrains.exposed.sql.transactions.transaction

class LoginController : Handler<LoginDto.Request, LoginDto.Response>(LoginDto.Request::class) {

    override fun process(
        requestDto: LoginDto.Request,
        clientData: AuthorizedClientData?
    ): DataResponse<LoginDto.Response> {
        val client = clientData.authenticatedClient

        val clientLastLogin = client.lastLogin
        transaction { ClientsDao.updateLastLogin(client.id) }

        return DataResponse(LoginDto.Response(clientLastLogin == null))
    }

}