package com.ruchij.services.todo

import com.ruchij.daos.todo.TodoItemDao
import com.ruchij.daos.todo.models.TodoItem
import com.ruchij.exceptions.ResourceNotFoundException
import com.ruchij.services.clock.Clock
import com.ruchij.services.random.RandomGenerator
import java.util.*

class TodoServiceImpl(
    private val clock: Clock,
    private val randomGenerator: RandomGenerator<UUID>,
    private val todoItemDao: TodoItemDao
) : TodoService {
    override fun create(title: String, description: String): TodoItem {
        val timestamp = clock.timestamp()
        val id = randomGenerator.generate()

        val todoItem = TodoItem(id, timestamp, timestamp, title, description, null)

        todoItemDao.insert(todoItem)

        return todoItem
    }

    override fun findById(id: UUID): TodoItem =
        todoItemDao.findById(id) ?: throw ResourceNotFoundException("")

    override fun deleteById(id: UUID): TodoItem {
        TODO("Not yet implemented")
    }

    override fun updateById(id: UUID, title: String?, description: String?, completion: Boolean?): TodoItem {
        TODO("Not yet implemented")
    }
}