package com.ruchij.api.services.health.models

import org.http4k.core.Body
import org.joda.time.DateTime
import com.ruchij.api.web.JacksonMappers.auto

data class ServiceInformation(val serviceName: String, val serviceVersion: String, val currentTimestamp: DateTime) {
    companion object {
        val lens = Body.auto<ServiceInformation>().toLens()
    }
}
