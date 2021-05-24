package com.ruchij.api.web.routes

import com.ruchij.api.services.health.HealthService
import com.ruchij.api.services.health.models.HealthCheck
import com.ruchij.api.services.health.models.ServiceInformation
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

object ServiceRoutes {
    fun create(healthService: HealthService): RoutingHttpHandler =
        routes(
            "/info" bind GET to {
                ServiceInformation.lens(healthService.serviceInformation(), Response(OK))
            },
            "/health-check" bind GET to {
                HealthCheck.lens(healthService.healthCheck(), Response(OK))
            }
        )
}