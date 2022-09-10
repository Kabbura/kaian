package com.narbase.kabbura.kaian.domain.client

import com.auth0.jwt.JWT
import com.narbase.kabbura.kaian.common.DataResponse
import com.narbase.kabbura.kaian.common.auth.AuthenticationConstants
import com.narbase.kabbura.kaian.common.auth.algorithm
import com.narbase.kabbura.kaian.common.auth.jwt.JwtTokenDto
import com.narbase.kabbura.kaian.common.auth.loggedin.AuthorizedClientData
import com.narbase.kabbura.kaian.common.exceptions.UnauthenticatedException
import com.narbase.kabbura.kaian.domain.client.token.AddTokenController
import com.narbase.kabbura.kaian.domain.client.token.RemoveTokenController
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.setupClientRoutes(jwtIssuer: String, jwtAudience: String) {
    authenticate(AuthenticationConstants.BASIC_AUTH) {
        post("/oauth/token") {
            val principal = call.principal<AuthorizedClientData>() ?: throw UnauthenticatedException()
            val privileges = principal.privileges.map { it.name }.toTypedArray()
            val token = generateJwtToken(jwtIssuer, jwtAudience, principal, privileges)

            val dataResponse = DataResponse(JwtTokenDto(token))
            call.respond(dataResponse)
        }
    }

    authenticate(AuthenticationConstants.JWT_AUTH) {
        route("/api/client") {
            route("/v1") {
                post("/token/add") { AddTokenController().handle(call) }
                post("/token/remove") { RemoveTokenController().handle(call) }
            }
        }
    }
}

fun generateJwtToken(
    jwtIssuer: String,
    jwtAudience: String,
    principal: AuthorizedClientData,
    privileges: Array<String>
) =
    JWT.create()
        .withIssuer(jwtIssuer)
        .withAudience(jwtAudience)
        .withClaim("clientId", principal.id)
        .withClaim("timestamp", principal.timestamp)
        .withArrayClaim("privileges", privileges)
        .sign(algorithm)