package com.ruchij.api

import com.ruchij.api.config.ApiConfiguration
import com.ruchij.api.daos.jdbi.JdbiConfiguration
import com.ruchij.api.daos.jdbi.mappers.DateTimeMapper
import com.ruchij.api.daos.jdbi.mappers.UuidMapper
import com.ruchij.api.daos.todo.JdbiTodoItemDao
import com.ruchij.api.services.health.HealthServiceImpl
import com.ruchij.api.services.random.RandomGenerator
import com.ruchij.api.services.todo.TodoServiceImpl
import com.ruchij.api.types.clock.Clock
import com.ruchij.api.types.clock.JodaTimeClock
import com.ruchij.api.web.Router
import com.ruchij.migration.runMigration
import com.sksamuel.hoplite.ConfigLoader
import org.http4k.routing.RoutingHttpHandler
import org.http4k.server.Netty
import org.http4k.server.asServer
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import java.util.*

fun main() {
    val apiConfiguration: ApiConfiguration =
        ConfigLoader().loadConfigOrThrow<ApiConfiguration>("/application.conf")

    val api: RoutingHttpHandler = runApi(apiConfiguration, JodaTimeClock, RandomGenerator.uuid)

    api.asServer(Netty(apiConfiguration.http.port)).start()
}

fun runApi(apiConfiguration: ApiConfiguration, clock: Clock, uuidGenerator: RandomGenerator<UUID>): RoutingHttpHandler {
    runMigration(apiConfiguration.database)

    val jdbi: Jdbi = JdbiConfiguration.create(apiConfiguration.database)

    val todoItemDao = JdbiTodoItemDao(jdbi)
    val todoService = TodoServiceImpl(clock, uuidGenerator, todoItemDao)

    val healthService = HealthServiceImpl(clock, jdbi)

    return Router.create(todoService, healthService)
}