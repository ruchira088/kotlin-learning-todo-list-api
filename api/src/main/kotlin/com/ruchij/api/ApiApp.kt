package com.ruchij.api

import com.ruchij.api.config.ApiConfiguration
import com.ruchij.api.types.clock.JodaTimeClock
import com.ruchij.api.services.health.HealthServiceImpl
import com.ruchij.api.web.Router
import org.http4k.server.Netty
import org.http4k.server.asServer

fun main() {
    val healthService = HealthServiceImpl(JodaTimeClock, TODO())

    Router.create(healthService).asServer(Netty(8000)).start()
}

fun runApi(apiConfiguration: ApiConfiguration) {

}