package com.narbase.kabbura.kaian.domain.admin.staff

import com.narbase.kabbura.kaian.common.DataResponse
import com.narbase.kabbura.kaian.common.Handler
import com.narbase.kabbura.kaian.common.auth.loggedin.AuthorizedClientData
import com.narbase.kabbura.kaian.data.tables.UsersTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

/*
 * Copyright 2017-2020 Narbase technologies and contributors. Use of this source code is governed by the MIT License.
 */

class EnableStaffController :
    Handler<EnableStaffController.RequestDto, EnableStaffController.ResponseDto>(RequestDto::class) {
    override fun process(requestDto: RequestDto, clientData: AuthorizedClientData?): DataResponse<ResponseDto> {

        transaction {
            UsersTable.update({
                (UsersTable.id eq requestDto.userId)
            }) {
                it[isInactive] = requestDto.isActive.not()
            }
        }
        return DataResponse()
    }

    class RequestDto(
        val userId: UUID,
        val isActive: Boolean

    )

    class ResponseDto()
}