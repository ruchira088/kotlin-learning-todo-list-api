package com.ruchij.services.todo

import com.ruchij.daos.todo.models.TodoItem
import java.util.*

interface TodoService {
    fun create(title: String, maybeDescription: String?): TodoItem

    fun findById(id: UUID): TodoItem

    fun deleteById(id: UUID): TodoItem

    fun updateById(id: UUID, maybeTitle: String?, maybeDescription: String?, maybeCompletion: Boolean?): TodoItem
}