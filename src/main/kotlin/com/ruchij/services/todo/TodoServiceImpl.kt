package com.ruchij.services.todo

import com.ruchij.daos.todo.TodoItemDao
import com.ruchij.daos.todo.models.TodoItem
import com.ruchij.exceptions.ResourceNotFoundException
import com.ruchij.types.clock.Clock
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
        todoItemDao.findById(id) ?: throw ResourceNotFoundException("Unable to find todo item with id = $id")

    override fun deleteById(id: UUID): TodoItem =
        todoItemDao.deleteById(id) ?: throw ResourceNotFoundException("Unable to find todo item with id = $id")

    override fun updateById(id: UUID, title: String?, description: String?, completion: Boolean?): TodoItem =
        findById(id).let {
            todoItem ->
                val timestamp = clock.timestamp()

                val updatedTodoItem =
                    todoItem.copy(
                        modifiedAt = timestamp,
                        title = title ?: todoItem.title,
                        description = description ?: todoItem.description,
                        completedAt = completion?.let { if (it) timestamp else null } ?: todoItem.completedAt
                    )

                todoItemDao.update(updatedTodoItem)

                return updatedTodoItem
        }
}