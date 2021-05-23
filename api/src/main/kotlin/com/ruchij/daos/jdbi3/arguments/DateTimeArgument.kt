package com.ruchij.daos.jdbi3.arguments

import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.statement.StatementContext
import org.joda.time.DateTime
import java.sql.PreparedStatement
import java.sql.Timestamp

class DateTimeArgument(private val dateTime: DateTime): Argument {

    override fun apply(position: Int, statement: PreparedStatement?, ctx: StatementContext?) {
        statement?.setTimestamp(position, Timestamp(dateTime.millis))
    }

}