package com.ruchij.api.services.health

import com.ruchij.api.services.health.models.HealthCheck
import com.ruchij.api.services.health.models.ServiceInformation

interface HealthService {
    fun serviceInformation(): ServiceInformation

    fun healthCheck(): HealthCheck
}