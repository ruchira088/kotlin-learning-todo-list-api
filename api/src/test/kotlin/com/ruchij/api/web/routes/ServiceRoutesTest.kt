package com.ruchij.api.web.routes

import com.ruchij.api.Providers
import com.ruchij.api.runApi
import com.ruchij.api.services.random.RandomGenerator
import com.ruchij.api.utils.TestExtensions.mustHaveHeader
import com.ruchij.api.utils.TestExtensions.mustHaveJsonBody
import com.ruchij.api.utils.TestExtensions.mustHaveStatus
import org.http4k.core.ContentType
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status
import org.joda.time.DateTime
import org.junit.jupiter.api.Test

class ServiceRoutesTest {
    @Test
    fun `Retrieve service information`() {
        val timestamp = DateTime.now()
        val clock = Providers.stubClock(timestamp)
        val apiConfig = Providers.apiConfiguration()

        val app = runApi(apiConfig, clock, RandomGenerator.uuid)
        val request = Request(GET, "/service/info")

        val response = app(request)

        val expectedJson =
            """{
               "serviceName":"todo-list-api",
               "serviceVersion":"0.0.1",
               "currentTimestamp":"$timestamp"
            }"""

        response mustHaveStatus Status.OK
        response mustHaveHeader ("Content-Type" to ContentType.APPLICATION_JSON.toHeaderValue())
        response mustHaveJsonBody expectedJson
    }
}