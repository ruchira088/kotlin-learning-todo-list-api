package com.ruchij.api.web.responses

import com.ruchij.api.web.JacksonMappers.auto
import org.http4k.core.Body

data class TodoItemsSearchResponse(
    val title: String?,
    val description: String?,
    val completed: Boolean?,
    val todoItems: List<TodoItemResponse>
) {
    companion object {
        val lens = Body.auto<TodoItemsSearchResponse>().toLens()
    }
}
