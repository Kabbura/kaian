package com.narbase.kabbura.kaian.domain.user.profile

import com.narbase.kabbura.kaian.common.DataResponse
import com.narbase.kabbura.kaian.common.Handler
import com.narbase.kabbura.kaian.common.auth.loggedin.AuthorizedClientData
import com.narbase.kabbura.kaian.common.exceptions.UnauthenticatedException
import com.narbase.kabbura.kaian.data.access.users.UsersRepository
import com.narbase.kabbura.kaian.data.conversions.users.toProfileDto
import com.narbase.kabbura.kaian.dto.domain.user.profile.GetProfileDto
import java.util.*

class GetProfileController : Handler<GetProfileDto.Request, GetProfileDto.Response>(GetProfileDto.Request::class) {

    override fun process(requestDto: GetProfileDto.Request, clientData: AuthorizedClientData?)
            : DataResponse<GetProfileDto.Response> {

        val clientId = UUID.fromString(clientData?.id ?: throw UnauthenticatedException())
        val userRm = UsersRepository.get(clientId)
        return DataResponse(GetProfileDto.Response(userRm.toProfileDto()))
    }
}