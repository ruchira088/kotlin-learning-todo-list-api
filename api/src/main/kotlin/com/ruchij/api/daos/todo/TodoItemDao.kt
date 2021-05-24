package com.ruchij.api.daos.todo

import com.ruchij.api.daos.todo.models.TodoItem
import java.util.*

interface TodoItemDao {
    fun insert(todoItem: TodoItem)

    fun findById(id: UUID): TodoItem?

    fun deleteById(id: UUID): TodoItem?

    fun update(todoItem: TodoItem)
}