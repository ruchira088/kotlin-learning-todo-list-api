package com.ruchij.services.health

import com.ruchij.services.health.models.HealthCheck
import com.ruchij.services.health.models.ServiceInformation

interface HealthService {
    fun serviceInformation(): ServiceInformation

    fun healthCheck(): HealthCheck
}