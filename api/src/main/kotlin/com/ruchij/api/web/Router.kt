package com.ruchij.api.web

import com.ruchij.api.services.health.HealthService
import com.ruchij.api.services.todo.TodoService
import com.ruchij.api.web.routes.ServiceRoutes
import com.ruchij.api.web.routes.TodoItemRoutes
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

object Router {
    fun create(todoService: TodoService, healthService: HealthService): RoutingHttpHandler =
        routes(
            "/todo-items" bind TodoItemRoutes.create(todoService),
            "/service" bind ServiceRoutes.create(healthService)
        )
}