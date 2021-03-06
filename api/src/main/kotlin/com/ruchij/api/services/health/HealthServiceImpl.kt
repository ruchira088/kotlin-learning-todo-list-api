package com.ruchij.api.services.health

import com.ruchij.api.services.health.models.HealthCheck
import com.ruchij.api.services.health.models.HealthStatus
import com.ruchij.api.types.clock.Clock
import com.ruchij.api.services.health.models.ServiceInformation
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo

class HealthServiceImpl(private val clock: Clock, private val jdbi: Jdbi): HealthService {

    override fun serviceInformation(): ServiceInformation =
        ServiceInformation("todo-list-api", "0.0.1", clock.timestamp())

    override fun healthCheck(): HealthCheck {
        val database = databaseHealthCheck()

        return HealthCheck(database)
    }

    private fun databaseHealthCheck(): HealthStatus =
        jdbi.inTransaction<HealthStatus, Exception> { handle ->
            handle.createQuery("SELECT 1")
                .setQueryTimeout(5)
                .mapTo<Int>()
                .one()
                .let { result -> if (result == 1) HealthStatus.Healthy else HealthStatus.Unhealthy }
        }

}