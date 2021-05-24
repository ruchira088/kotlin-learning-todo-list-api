package com.ruchij.api.services.todo

import com.ruchij.api.daos.todo.TodoItemDao
import com.ruchij.api.daos.todo.models.TodoItem
import com.ruchij.api.exceptions.ResourceNotFoundException
import com.ruchij.api.types.clock.Clock
import com.ruchij.api.services.random.RandomGenerator
import com.ruchij.api.utils.Extensions.foldNullable
import java.util.*

class TodoServiceImpl(
    private val clock: Clock,
    private val randomGenerator: RandomGenerator<UUID>,
    private val todoItemDao: TodoItemDao
) : TodoService {
    override fun create(title: String, maybeDescription: String?): TodoItem {
        val timestamp = clock.timestamp()
        val id = randomGenerator.generate()

        val todoItem = TodoItem(id, timestamp, timestamp, title, maybeDescription, null)

        todoItemDao.insert(todoItem)

        return todoItem
    }

    override fun findById(id: UUID): TodoItem =
        todoItemDao.findById(id) ?: throw ResourceNotFoundException("Unable to find todo item with id = $id")

    override fun deleteById(id: UUID): TodoItem =
        todoItemDao.deleteById(id) ?: throw ResourceNotFoundException("Unable to find todo item with id = $id")

    override fun updateById(id: UUID, maybeTitle: String?, maybeDescription: String?, maybeCompletion: Boolean?): TodoItem =
        findById(id).let {
            todoItem ->
                val timestamp = clock.timestamp()

                val updatedTodoItem =
                    todoItem.copy(
                        modifiedAt = timestamp,
                        title = maybeTitle ?: todoItem.title,
                        description = maybeDescription.foldNullable({ todoItem.description}) { it.takeUnless { value -> value.isEmpty() }},
                        completedAt = maybeCompletion.foldNullable({ todoItem.completedAt }) { completed -> timestamp.takeUnless { completed } }
                    )

                todoItemDao.update(updatedTodoItem)

                return updatedTodoItem
        }
}