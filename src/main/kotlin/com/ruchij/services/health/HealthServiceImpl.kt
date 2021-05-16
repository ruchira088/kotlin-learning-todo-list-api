package com.ruchij.services.health

import com.ruchij.services.clock.Clock
import com.ruchij.services.health.models.ServiceInformation

class HealthServiceImpl(private val clock: Clock): HealthService {
    override fun serviceInformation(): ServiceInformation =
        ServiceInformation("todo-list-api", "0.0.1", clock.timestamp())
}