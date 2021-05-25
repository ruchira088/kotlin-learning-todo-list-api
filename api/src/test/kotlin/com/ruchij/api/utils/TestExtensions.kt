package com.ruchij.api.utils

import com.fasterxml.jackson.databind.ObjectMapper
import org.http4k.core.Response
import org.http4k.core.Status
import kotlin.test.assertEquals

object TestExtensions {
    infix fun Response.mustHaveJsonBody(json: String) =
        assertEquals(ObjectMapper().readTree(json), ObjectMapper().readTree(bodyString()))

    infix fun Response.mustHaveStatus(status: Status) =
        assertEquals(status, this.status)

    infix fun Response.mustHaveHeader(headerValue: Pair<String, String>) {
        val (name, value) = headerValue
        assertEquals(value, header(name))
    }
}