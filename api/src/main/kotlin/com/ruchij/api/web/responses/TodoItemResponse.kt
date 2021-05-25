package com.ruchij.api.web.responses

import com.ruchij.api.daos.todo.models.TodoItem
import com.ruchij.api.web.JacksonMappers.auto
import org.http4k.core.Body
import org.joda.time.DateTime
import java.util.*

data class TodoItemResponse(
    val id: UUID,
    val createdAt: DateTime,
    val title: String,
    val description: String?,
    val completedAt: DateTime?
) {
    companion object {
        val lens = Body.auto<TodoItemResponse>().toLens()

        fun from(todoItem: TodoItem): TodoItemResponse =
            TodoItemResponse(
                todoItem.id,
                todoItem.createdAt,
                todoItem.title,
                todoItem.description,
                todoItem.completedAt
            )
    }
}
