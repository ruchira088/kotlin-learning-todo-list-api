package com.ruchij.api.services.health.models

import org.http4k.core.Body
import com.ruchij.api.web.JacksonMappers.auto

data class HealthCheck(val database: HealthStatus) {
    companion object {
        val lens = Body.auto<HealthCheck>().toLens()
    }
}
