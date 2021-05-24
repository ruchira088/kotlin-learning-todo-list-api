package com.ruchij.api.web.routes

import com.ruchij.api.services.todo.TodoService
import org.http4k.core.Method.POST
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

object TodoItemRoutes {
    fun create(todoService: TodoService): RoutingHttpHandler =
        routes(
            "/todo-item" bind POST to { request -> TODO()
            }
        )
}