package com.ruchij

import com.ruchij.services.clock.JodaTimeClock
import com.ruchij.services.health.HealthServiceImpl
import com.ruchij.web.Router
import org.http4k.server.Netty
import org.http4k.server.asServer

fun main() {
    val healthService = HealthServiceImpl(JodaTimeClock)

    Router.create(healthService).asServer(Netty(8000)).start()
}