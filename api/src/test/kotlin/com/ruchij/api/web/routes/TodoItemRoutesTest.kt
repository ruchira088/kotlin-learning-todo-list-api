package com.ruchij.api.web.routes

import com.ruchij.api.Providers
import com.ruchij.api.daos.jdbi.JdbiConfiguration
import com.ruchij.api.daos.todo.JdbiTodoItemDao
import com.ruchij.api.daos.todo.models.TodoItem
import com.ruchij.api.services.random.RandomGenerator
import org.joda.time.DateTime
import com.ruchij.api.runApi
import com.ruchij.api.utils.TestExtensions.mustHaveHeader
import com.ruchij.api.utils.TestExtensions.mustHaveJsonBody
import com.ruchij.api.utils.TestExtensions.mustHaveStatus
import org.http4k.core.ContentType
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Status
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TodoItemRoutesTest {
    @Test
    fun `Create todo item`() {
        val apiConfig = Providers.apiConfiguration()

        val timestamp = DateTime.now()
        val clock = Providers.stubClock(timestamp)

        val uuid = UUID.randomUUID()

        val app = runApi(apiConfig, clock, RandomGenerator.constant(uuid))

        val requestBody =
            """{
                "title": "Learn Scala 3",
                "description": "Do the Scala 3 Coursera course"
            }"""

        val request =
            Request(POST, "/todo-items")
                .body(requestBody)
                .header("Content-Type", ContentType.APPLICATION_JSON.toHeaderValue())

        val response = app(request)

        val expectedJson =
            """{
                 "id": "$uuid",
                 "createdAt": "$timestamp",
                 "title": "Learn Scala 3",
                 "description": "Do the Scala 3 Coursera course",
                 "completedAt": null
            }"""

        response mustHaveStatus Status.CREATED
        response mustHaveHeader ("Content-Type" to ContentType.APPLICATION_JSON.toHeaderValue())
        response mustHaveJsonBody expectedJson

        val todoItemDao = JdbiTodoItemDao(JdbiConfiguration.create(apiConfig.database))

        val todoItem = todoItemDao.findById(uuid)
        assertNotNull(todoItem)

        assertEquals(
            TodoItem(uuid, timestamp, timestamp, "Learn Scala 3", "Do the Scala 3 Coursera course", null),
            todoItem
        )
    }
}