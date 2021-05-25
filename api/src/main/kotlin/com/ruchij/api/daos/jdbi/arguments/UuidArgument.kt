package com.ruchij.api.daos.jdbi.arguments

import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.statement.StatementContext
import java.sql.PreparedStatement
import java.util.*

class UuidArgument(private val uuid: UUID): Argument {

    override fun apply(position: Int, statement: PreparedStatement?, ctx: StatementContext?) {
        statement?.setString(position, uuid.toString())
    }

}