package com.ruchij.api.config

import com.ruchij.migration.config.DatabaseConfig

data class ApiConfiguration(val http: HttpConfiguration, val database: DatabaseConfig)
