package com.narbase.kabbura.kaian.domain.user.profile

import com.narbase.kabbura.kaian.common.DataResponse
import com.narbase.kabbura.kaian.common.Handler
import com.narbase.kabbura.kaian.common.auth.loggedin.AuthorizedClientData
import com.narbase.kabbura.kaian.common.exceptions.UnauthenticatedException
import com.narbase.kabbura.kaian.data.tables.UsersTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class UpdateProfileController : Handler<UpdateProfileController.RequestDto, Unit>(
    RequestDto::class
) {
    @Suppress("RemoveRedundantQualifierName")
    override fun process(requestDto: RequestDto, clientData: AuthorizedClientData?): DataResponse<Unit> {
        val clientId = UUID.fromString(clientData?.id ?: throw UnauthenticatedException())
        transaction {
            UsersTable.update({ UsersTable.clientId eq clientId }) {
                it[UsersTable.callingCode] = requestDto.callingCode
                it[UsersTable.localPhone] = requestDto.localPhone
                it[UsersTable.fullName] = requestDto.fullName
            }
        }
        return DataResponse()
    }

    class RequestDto(
        val fullName: String,
        val callingCode: String,
        val localPhone: String
    )

    class ResponseDto()
}