package com.ruchij.api.web

import com.ruchij.api.services.health.HealthService
import com.ruchij.api.web.routes.ServiceRoutes
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

object Router {
    fun create(healthService: HealthService): RoutingHttpHandler =
        routes(
            "/service" bind ServiceRoutes.create(healthService)
        )
}