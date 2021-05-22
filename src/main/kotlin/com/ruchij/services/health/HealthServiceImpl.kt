package com.ruchij.services.health

import com.ruchij.services.health.models.HealthCheck
import com.ruchij.services.health.models.HealthStatus
import com.ruchij.types.clock.Clock
import com.ruchij.services.health.models.ServiceInformation
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo

class HealthServiceImpl(private val clock: Clock, private val handle: Handle): HealthService {

    override fun serviceInformation(): ServiceInformation =
        ServiceInformation("todo-list-api", "0.0.1", clock.timestamp())

    override fun healthCheck(): HealthCheck {
        val database = databaseHealthCheck()

        return HealthCheck(database)
    }

    private fun databaseHealthCheck(): HealthStatus =
        handle.createQuery("SELECT 1")
            .setQueryTimeout(5)
            .mapTo<Int>()
            .one()
            .let { result -> if (result == 1) HealthStatus.Healthy else HealthStatus.Unhealthy }
}