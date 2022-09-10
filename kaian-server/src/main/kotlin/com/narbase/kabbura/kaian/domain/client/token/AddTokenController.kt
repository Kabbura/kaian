package com.narbase.kabbura.kaian.domain.client.token

import com.google.gson.annotations.SerializedName
import com.narbase.kabbura.kaian.common.DataResponse
import com.narbase.kabbura.kaian.common.Handler
import com.narbase.kabbura.kaian.common.auth.loggedin.AuthorizedClientData
import com.narbase.kabbura.kaian.common.exceptions.UnauthenticatedException
import com.narbase.kabbura.kaian.data.tables.ClientsTable
import com.narbase.kabbura.kaian.data.tables.DeviceTokensTable
import com.narbase.kabbura.kaian.data.tables.utils.toEntityId
import com.narbase.kabbura.kaian.domain.client.token.AddTokenController.RequestDto
import com.narbase.kabbura.kaian.domain.client.token.AddTokenController.ResponseDto
import com.narbase.kabbura.kaian.domain.client.token.RemoveTokenController.Companion.removeClientToken
import com.narbase.kabbura.kaian.domain.utils.client
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class AddTokenController : Handler<RequestDto, ResponseDto>(RequestDto::class) {

    class RequestDto(
        @SerializedName("token")
        val token: String
    )

    class ResponseDto

    override fun process(requestDto: RequestDto, clientData: AuthorizedClientData?): DataResponse<ResponseDto> {
        val client = clientData?.client ?: throw UnauthenticatedException()
        transaction {
            removeClientToken(client, requestDto.token)
            DeviceTokensTable.insert {
                it[token] = requestDto.token
                it[clientId] = client.id.toEntityId(ClientsTable)
                it[createdOn] = DateTime()
            }
        }
        return DataResponse(ResponseDto())
    }
}