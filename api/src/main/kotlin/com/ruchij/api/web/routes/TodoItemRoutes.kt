package com.ruchij.api.web.routes

import com.ruchij.api.services.todo.TodoService
import com.ruchij.api.utils.Extensions.foldNullable
import com.ruchij.api.web.requests.CreateTodoItemRequest
import com.ruchij.api.web.requests.UpdateTodoItemRequest
import com.ruchij.api.web.responses.TodoItemResponse
import org.http4k.core.Method.GET
import org.http4k.core.Method.PATCH
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.lens.Path
import org.http4k.lens.uuid
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

object TodoItemRoutes {
    private val todoItemIdLens = Path.uuid().of("id", "ID of the todo item")

    fun create(todoService: TodoService): RoutingHttpHandler =
        routes(
            "/" bind POST to { request ->
                val (title, maybeDescription) = CreateTodoItemRequest.lens(request)

                val todoItem = todoService.create(title, maybeDescription)

                TodoItemResponse.lens(TodoItemResponse.from(todoItem), Response(Status.CREATED))
            },
            "/{id}" bind GET to { request ->
                val id = todoItemIdLens(request)

                val maybeTodoItem = todoService.findById(id)

                maybeTodoItem.foldNullable({ Response(Status.NOT_FOUND) }) {
                    todoItem ->
                        TodoItemResponse.lens(TodoItemResponse.from(todoItem), Response(Status.OK))
                }
            },
            "/{id}" bind PATCH to { request ->
                val id = todoItemIdLens(request)
                val (maybeTitle, maybeDescription, maybeCompleted) = UpdateTodoItemRequest.lens(request)

                val todoItem = todoService.updateById(id, maybeTitle, maybeDescription, maybeCompleted)

                TodoItemResponse.lens(TodoItemResponse.from(todoItem), Response(Status.OK))
            }
        )
}