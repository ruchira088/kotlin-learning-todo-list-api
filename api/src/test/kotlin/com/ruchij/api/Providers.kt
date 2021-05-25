package com.ruchij.api

import com.ruchij.api.config.ApiConfiguration
import com.ruchij.api.config.HttpConfiguration
import com.ruchij.api.services.random.RandomGenerator
import com.ruchij.api.types.clock.Clock
import com.ruchij.migration.config.DatabaseConfig
import org.joda.time.DateTime

object Providers {
    fun stubClock(dateTime: DateTime): Clock =
        Clock { dateTime }

    fun apiConfiguration(): ApiConfiguration =
        ApiConfiguration(
            HttpConfiguration(80),
            databaseConfiguration(RandomGenerator.uuid.generate().toString().takeLastWhile { it != '-' }),
        )

    private fun databaseConfiguration(prefix: String): DatabaseConfig =
        DatabaseConfig("jdbc:h2:mem:$prefix-todo-list;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false", "", "")
}