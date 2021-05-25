package com.ruchij.api.web.requests

import com.ruchij.api.web.JacksonMappers.auto
import org.http4k.core.Body

data class CreateTodoItemRequest(val title: String, val description: String?) {
    companion object {
        val lens = Body.auto<CreateTodoItemRequest>().toLens()
    }
}
