package com.ruchij.services.health

import com.ruchij.services.health.models.ServiceInformation

interface HealthService {
    fun serviceInformation(): ServiceInformation
}