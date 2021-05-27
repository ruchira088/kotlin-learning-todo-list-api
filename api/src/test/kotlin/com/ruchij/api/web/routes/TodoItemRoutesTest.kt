package com.ruchij.api.web.routes

import com.ruchij.api.Providers
import com.ruchij.api.daos.jdbi.JdbiConfiguration
import com.ruchij.api.daos.todo.JdbiTodoItemDao
import com.ruchij.api.daos.todo.models.TodoItem
import com.ruchij.api.runApi
import com.ruchij.api.services.random.RandomGenerator
import com.ruchij.api.types.clock.JodaTimeClock
import com.ruchij.api.utils.TestExtensions.mustHaveHeader
import com.ruchij.api.utils.TestExtensions.mustHaveJsonBody
import com.ruchij.api.utils.TestExtensions.mustHaveStatus
import org.http4k.core.ContentType
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Status
import org.joda.time.DateTime
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

    @Test
    fun `Search todo items`() {
        val todoItemOne: TodoItem =
            TodoItem(
                UUID.randomUUID(),
                DateTime.now(),
                DateTime.now(),
                "Learn Scala 3",
                "Do the Scala 3 Coursera course",
                null
            )

        val todoItemTwo: TodoItem =
            TodoItem(
                UUID.randomUUID(),
                DateTime.now(),
                DateTime.now(),
                "Go to the gym",
                "Try to go to the gym everyday",
                DateTime.now()
            )

        val apiConfig = Providers.apiConfiguration()
        val app = runApi(apiConfig, JodaTimeClock, RandomGenerator.uuid)

        val todoItemDao = JdbiTodoItemDao(JdbiConfiguration.create(apiConfig.database))

        todoItemDao.insert(todoItemOne)
        todoItemDao.insert(todoItemTwo)

        val retrieveAllRequest = Request(GET, "/todo-items")
        val retrieveAllResponse = app(retrieveAllRequest)

        retrieveAllResponse mustHaveStatus Status.OK
        retrieveAllResponse mustHaveJsonBody """{
               "title": null,
               "description": null,
               "completed": null,
               "todoItems": [
                  {
                     "id": "${todoItemOne.id}",
                     "createdAt": "${todoItemOne.createdAt}",
                     "title": "Learn Scala 3",
                     "description": "Do the Scala 3 Coursera course",
                     "completedAt": null
                  },
                  {
                     "id": "${todoItemTwo.id}",
                     "createdAt": "${todoItemTwo.createdAt}",
                     "title": "Go to the gym",
                     "description": "Try to go to the gym everyday",
                     "completedAt": "${todoItemTwo.completedAt}"
                  }
               ]
            }"""

        val retrieveCompletedRequest = Request(GET, "/todo-items?completed=true")
        val retrieveCompletedResponse = app(retrieveCompletedRequest)

        retrieveCompletedResponse mustHaveStatus Status.OK
        retrieveCompletedResponse mustHaveJsonBody """{
               "title": null,
               "description": null,
               "completed": true,
               "todoItems": [
                  {
                     "id": "${todoItemTwo.id}",
                     "createdAt": "${todoItemTwo.createdAt}",
                     "title": "Go to the gym",
                     "description": "Try to go to the gym everyday",
                     "completedAt": "${todoItemTwo.completedAt}"
                  }
               ]
            }"""

        val searchByTitleRequest = Request(GET, "/todo-items?title=Scala")
        val searchByTitleResponse = app(searchByTitleRequest)

        searchByTitleResponse mustHaveStatus Status.OK
        searchByTitleResponse mustHaveJsonBody """{
               "title": "Scala",
               "description": null,
               "completed": null,
               "todoItems": [
                  {
                     "id": "${todoItemOne.id}",
                     "createdAt": "${todoItemOne.createdAt}",
                     "title": "Learn Scala 3",
                     "description": "Do the Scala 3 Coursera course",
                     "completedAt": null
                  }
               ]
            }"""

        val searchByDescriptionRequest = Request(GET, "/todo-items?description=everyday")
        val searchByDescriptionResponse = app(searchByDescriptionRequest)

        searchByDescriptionResponse mustHaveStatus Status.OK
        searchByDescriptionResponse mustHaveJsonBody """{
               "title": null,
               "description": "everyday",
               "completed": null,
                "todoItems": [
                  {
                     "id": "${todoItemTwo.id}",
                     "createdAt": "${todoItemTwo.createdAt}",
                     "title": "Go to the gym",
                     "description": "Try to go to the gym everyday",
                     "completedAt": "${todoItemTwo.completedAt}"
                  }
               ]
            }"""
    }
}