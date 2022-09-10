package com.narbase.kabbura.kaian.dto.models.roles

import com.narbase.kabbura.kaian.dto.common.IdDto

typealias PrivilegeName = String

data class RoleDto(
    val id: IdDto?,
    val name: String,
    val privileges: List<PrivilegeName>,
)