package com.narbase.kabbura.kaian.domain.client.token

import com.google.gson.annotations.SerializedName
import com.narbase.kabbura.kaian.common.DataResponse
import com.narbase.kabbura.kaian.common.Handler
import com.narbase.kabbura.kaian.common.auth.loggedin.AuthorizedClientData
import com.narbase.kabbura.kaian.common.exceptions.UnauthenticatedException
import com.narbase.kabbura.kaian.data.models.clients.Client
import com.narbase.kabbura.kaian.data.tables.DeviceTokensTable
import com.narbase.kabbura.kaian.domain.client.token.RemoveTokenController.RequestDto
import com.narbase.kabbura.kaian.domain.client.token.RemoveTokenController.ResponseDto
import com.narbase.kabbura.kaian.domain.utils.client
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

class RemoveTokenController : Handler<RequestDto, ResponseDto>(RequestDto::class) {

    class RequestDto(
        @SerializedName("token")
        val token: String
    )

    class ResponseDto

    override fun process(requestDto: RequestDto, clientData: AuthorizedClientData?): DataResponse<ResponseDto> {
        val client = clientData?.client ?: throw UnauthenticatedException()
        transaction {
            removeClientToken(client, requestDto.token)
        }
        return DataResponse(ResponseDto())
    }

    companion object {
        fun removeClientToken(client: Client, token: String) {
            DeviceTokensTable.deleteWhere {
                (DeviceTokensTable.token eq token) and (DeviceTokensTable.clientId eq client.id)
            }

        }
    }
}