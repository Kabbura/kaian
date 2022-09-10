package com.narbase.kabbura.kaian.domain.admin

import com.narbase.kabbura.kaian.common.auth.AuthenticationConstants
import com.narbase.kabbura.kaian.domain.admin.staff.EnableStaffController
import com.narbase.kabbura.kaian.domain.admin.staff.UsersCrudController
import com.narbase.kabbura.kaian.domain.admin.staff.roles.RolesCurdController
import com.narbase.kabbura.kaian.domain.user.crud.crud
import com.narbase.kabbura.kaian.domain.utils.addInactiveUserInterceptor
import com.narbase.kabbura.kaian.dto.models.roles.Privilege
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

/*
 * Copyright 2017-2020 Narbase technologies and contributors. Use of this source code is governed by the MIT License.
 */

fun Routing.setupAdminRoutes() {
    authenticate(AuthenticationConstants.JWT_AUTH) {
        route("/api/admin") {
            addInactiveUserInterceptor()
            route("/v1") {

                route("/settings") {
                    crud(
                        "/users",
                        UsersCrudController(),
                        Privilege.UsersManagement,
                    )
                    crud(
                        "/roles",
                        RolesCurdController(),
                        Privilege.UsersManagement,
                    )
                    post("/enable_user") {
                        EnableStaffController().handle(call)
                    }
                }
            }
        }
    }
}