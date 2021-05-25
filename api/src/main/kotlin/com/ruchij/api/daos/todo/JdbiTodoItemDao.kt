package com.ruchij.api.daos.todo

import com.ruchij.api.daos.jdbi.arguments.DateTimeArgument
import com.ruchij.api.daos.jdbi.arguments.UuidArgument
import com.ruchij.api.daos.todo.models.TodoItem
import com.ruchij.api.utils.Extensions.nullableType
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo
import java.lang.Exception
import java.util.*

class JdbiTodoItemDao(private val jdbi: Jdbi) : TodoItemDao {

    override fun insert(todoItem: TodoItem) =
        jdbi.useHandle<Exception> { handle ->
            handle.createUpdate(
                """
                INSERT INTO todo_item (id, created_at, modified_at, title, description, completed_at)
                    VALUES (:id, :createdAt, :modifiedAt, :title, :description, :completedAt)
           """
            )
                .bind("id", UuidArgument(todoItem.id))
                .bind("createdAt", DateTimeArgument(todoItem.createdAt))
                .bind("modifiedAt", DateTimeArgument(todoItem.modifiedAt))
                .bind("title", todoItem.title)
                .bind("description", todoItem.description)
                .bind("completedAt", todoItem.completedAt)
                .one()
        }

    override fun findById(id: UUID): TodoItem? =
        jdbi.inTransaction<TodoItem?, Exception> { handle -> findById(id, handle) }

    override fun deleteById(id: UUID): TodoItem? =
        jdbi.inTransaction<TodoItem?, Exception> { handle ->
            findById(id, handle)?.also {
                handle.createUpdate("DELETE FROM todo_item WHERE id = :id")
                    .bind("id", UuidArgument(id))
                    .one()
            }
        }

    private fun findById(id: UUID, handle: Handle): TodoItem? =
        handle.createQuery(
            """
                    SELECT id, created_at, modified_at, title, description, completed_at FROM todo_item
                        WHERE id = :id
                """
        )
            .bind("id", UuidArgument(id))
            .mapTo<TodoItem>()
            .findOne()
            .nullableType()

    override fun update(todoItem: TodoItem) =
        jdbi.useTransaction<Exception> { handle ->
            handle.createUpdate(
                """
               UPDATE todo_item
                    SET 
                        modified_at = :modifiedAt,
                        title = :title,
                        description = :description,
                        completed_at = :completedAt
                        
            """
            )
                .bind("modifiedAt", DateTimeArgument(todoItem.modifiedAt))
                .bind("title", todoItem.title)
                .bind("description", todoItem.description)
                .bind("completedAt", todoItem.completedAt)
                .one()
        }
}