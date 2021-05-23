package com.ruchij.migration

import com.ruchij.migration.config.DatabaseConfig
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.output.MigrateResult

fun main() {

}

fun runMigration(databaseConfig: DatabaseConfig): MigrateResult {
    val flyway =
        Flyway.configure()
            .dataSource(databaseConfig.url, databaseConfig.user, databaseConfig.password)
            .load()

    val result: MigrateResult = flyway.migrate()

    return result
}