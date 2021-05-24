package com.ruchij.api.daos.todo.models

import org.joda.time.DateTime
import java.util.*

data class TodoItem(
    val id: UUID,
    val createdAt: DateTime,
    val modifiedAt: DateTime,
    val title: String,
    val description: String?,
    val completedAt: DateTime?
)
