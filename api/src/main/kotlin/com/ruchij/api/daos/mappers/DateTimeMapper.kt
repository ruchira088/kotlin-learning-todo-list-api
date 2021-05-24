package com.ruchij.api.daos.mappers

import org.jdbi.v3.core.mapper.ColumnMapper
import org.jdbi.v3.core.statement.StatementContext
import org.joda.time.DateTime
import java.lang.IllegalStateException
import java.sql.ResultSet

object DateTimeMapper: ColumnMapper<DateTime> {

    override fun map(resultSet: ResultSet?, columnNumber: Int, ctx: StatementContext?): DateTime =
        resultSet?.getTimestamp(columnNumber)?.time?.let { DateTime(it) } ?:
            throw IllegalStateException("Unable to map value to DateTime")

}