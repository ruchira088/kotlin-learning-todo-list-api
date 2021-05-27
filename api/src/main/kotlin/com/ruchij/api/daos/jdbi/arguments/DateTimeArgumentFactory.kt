package com.ruchij.api.daos.jdbi.arguments

import org.jdbi.v3.core.argument.AbstractArgumentFactory
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.config.ConfigRegistry
import org.joda.time.DateTime
import java.sql.Timestamp
import java.sql.Types

object DateTimeArgumentFactory: AbstractArgumentFactory<DateTime>(Types.TIMESTAMP) {
    override fun build(value: DateTime, config: ConfigRegistry?): Argument =
        Argument { position, statement, _ ->
            statement.setTimestamp(position, Timestamp(value.millis))
        }
}