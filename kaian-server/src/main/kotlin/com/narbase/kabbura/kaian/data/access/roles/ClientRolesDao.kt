package com.narbase.kabbura.kaian.data.access.roles

import com.narbase.kabbura.kaian.data.models.roles.Role
import com.narbase.kabbura.kaian.data.tables.ClientsTable
import com.narbase.kabbura.kaian.data.tables.roles.ClientsRolesTable
import com.narbase.kabbura.kaian.data.tables.roles.RolesTable
import com.narbase.kabbura.kaian.data.tables.utils.toEntityId
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.leftJoin
import org.jetbrains.exposed.sql.select
import java.util.*

/*
 * Copyright 2017-2020 Narbase technologies and contributors. Use of this source code is governed by the MIT License.
 */
object ClientRolesDao {
    fun getClientRoles(clientId: UUID): List<Role> {
        val dynamicRolesDao = RolesDao
        return ClientsRolesTable
            .leftJoin(RolesTable, { dynamicRoleId }, { RolesTable.id })
            .select {
                ClientsRolesTable.clientId eq clientId
            }.map(dynamicRolesDao::toModel)
    }

    fun saveClientRoles(clientId: UUID, rolesIds: List<UUID>) {
        ClientsRolesTable.deleteWhere {
            ClientsRolesTable.clientId eq clientId
        }
        ClientsRolesTable.batchInsert(rolesIds) { roleId ->
            this[ClientsRolesTable.clientId] = clientId.toEntityId(ClientsTable)
            this[ClientsRolesTable.dynamicRoleId] = roleId.toEntityId(RolesTable)
        }
    }
}