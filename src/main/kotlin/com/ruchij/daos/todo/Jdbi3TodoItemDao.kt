package com.ruchij.daos.todo

import com.ruchij.daos.jdbi3.DateTimeArgument
import com.ruchij.daos.jdbi3.UuidArgument
import com.ruchij.daos.todo.models.TodoItem
import com.ruchij.utils.Extensions.nullableType
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import java.util.*

class Jdbi3TodoItemDao(private val handle: Handle): TodoItemDao {

    override fun insert(todoItem: TodoItem) =
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

    override fun findById(id: UUID): TodoItem? =
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

    override fun deleteById(id: UUID): TodoItem? =
        findById(id)?.also {
            handle.createUpdate(
                "DELETE FROM todo_item WHERE id = :id"
            )
                .bind("id", UuidArgument(id))
                .one()
        }

    override fun update(todoItem: TodoItem) =
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