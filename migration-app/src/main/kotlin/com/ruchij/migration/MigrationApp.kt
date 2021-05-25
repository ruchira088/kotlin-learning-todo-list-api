package com.ruchij.migration

import com.ruchij.migration.config.DatabaseConfig
import com.ruchij.migration.config.MigrationConfig
import com.ruchij.migration.models.MigrationResult
import com.sksamuel.hoplite.ConfigLoader
import org.flywaydb.core.Flyway
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("com.ruchij.migration.MigrationApp")

fun main() {
    val migrationConfig: MigrationConfig =
        ConfigLoader().loadConfigOrThrow<MigrationConfig>("/application.conf")

    val result = runMigration(migrationConfig.database)

    logger.info("Migrations executed: ${result.migrationsExecuted}")
    logger.info("Schema version: ${result.targetSchemaVersion}")
}

fun runMigration(databaseConfig: DatabaseConfig): MigrationResult {
    val flyway =
        Flyway.configure()
            .dataSource(databaseConfig.url, databaseConfig.user, databaseConfig.password)
            .load()

    val result = flyway.migrate()

    return MigrationResult(result.migrationsExecuted, result.targetSchemaVersion)
}