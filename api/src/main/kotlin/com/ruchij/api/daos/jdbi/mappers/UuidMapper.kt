package com.ruchij.api.daos.jdbi.mappers

import org.jdbi.v3.core.mapper.ColumnMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.util.*

object UuidMapper: ColumnMapper<UUID?> {

    override fun map(resultSet: ResultSet?, columnNumber: Int, ctx: StatementContext?): UUID? =
        resultSet?.getString(columnNumber)?.let { UUID.fromString(it) }

}