package com.ruchij.web

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.ruchij.services.health.models.HealthStatus
import org.http4k.format.ConfigurableJackson
import org.http4k.format.asConfigurable
import org.http4k.format.text
import org.http4k.format.withStandardMappings
import org.joda.time.DateTime

object JacksonMappers: ConfigurableJackson(
    KotlinModule()
        .asConfigurable()
        .withStandardMappings()
        .text { dateTime: DateTime -> dateTime.toString() }
        .text { healthStatus: HealthStatus -> healthStatus.name }
        .done()
)