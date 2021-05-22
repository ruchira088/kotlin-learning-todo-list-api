package com.ruchij.web

import com.ruchij.services.health.HealthService
import com.ruchij.web.routes.ServiceRoutes
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

object Router {
    fun create(healthService: HealthService): RoutingHttpHandler =
        routes(
            "/service" bind ServiceRoutes.create(healthService)
        )
}