package com.ruchij.migration.models

data class MigrationResult(val migrationsExecuted: Int, val targetSchemaVersion: String)
