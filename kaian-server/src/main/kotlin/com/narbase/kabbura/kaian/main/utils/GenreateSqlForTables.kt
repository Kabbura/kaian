package com.narbase.kabbura.kaian.main.utils

import com.narbase.kabbura.kaian.common.db.DatabaseConnector
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

/*
 * Copyright 2017-2020 Narbase technologies and contributors. Use of this source code is governed by the MIT License.
 */

fun generateSqlForTables(vararg tables: Table) {
    DatabaseConnector.connect()
    transaction {
        println("\n\nPrint statements\n\n")
        tables.forEach { table ->
            table.createStatement().forEach {
                println("$it ;")
            }
        }
    }

}