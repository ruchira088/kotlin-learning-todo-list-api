package com.ruchij.web.responses

import com.ruchij.web.JacksonMappers.auto
import org.http4k.core.Body
import org.joda.time.DateTime
import java.util.*

data class TodoItemResponse(
    val id: UUID,
    val createdAt: DateTime,
    val title: String,
    val description: String?,
    val completedAt: String?
) {
    companion object {
        val lens = Body.auto<TodoItemResponse>().toLens()
    }
}
