package com.ruchij.daos.todo

import com.ruchij.daos.todo.models.TodoItem
import java.util.*

interface TodoItemDao {
    fun insert(todoItem: TodoItem)

    fun findById(id: UUID): TodoItem?

    fun deleteById(id: UUID): TodoItem?

    fun update(todoItem: TodoItem)
}