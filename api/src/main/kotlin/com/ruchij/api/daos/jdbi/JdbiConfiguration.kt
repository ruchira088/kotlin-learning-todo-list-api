package com.ruchij.api.daos.jdbi

import com.ruchij.api.daos.jdbi.mappers.DateTimeMapper
import com.ruchij.api.daos.jdbi.mappers.UuidMapper
import com.ruchij.migration.config.DatabaseConfig
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin

object JdbiConfiguration {
    fun create(databaseConfig: DatabaseConfig): Jdbi =
        Jdbi.create(databaseConfig.url, databaseConfig.user, databaseConfig.password)
            .installPlugin(KotlinPlugin())
            .registerColumnMapper(DateTimeMapper)
            .registerColumnMapper(UuidMapper)
}