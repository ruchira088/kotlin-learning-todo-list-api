package com.ruchij.api.web.requests

import com.ruchij.api.web.JacksonMappers.auto
import org.http4k.core.Body

data class UpdateTodoItemRequest(val title: String?, val description: String?, val completed: Boolean?) {
    companion object {
        val lens = Body.auto<UpdateTodoItemRequest>().toLens()
    }
}
