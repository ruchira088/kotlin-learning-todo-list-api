package com.ruchij.api.daos.jdbi.arguments

import org.jdbi.v3.core.argument.AbstractArgumentFactory
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.config.ConfigRegistry
import java.sql.Types
import java.util.*

object UuidArgumentFactory: AbstractArgumentFactory<UUID>(Types.VARCHAR) {
    override fun build(value: UUID, config: ConfigRegistry?): Argument =
        Argument { position, statement, _ -> statement.setString(position, value.toString()) }
}