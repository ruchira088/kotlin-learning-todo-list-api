package com.ruchij.api.services.random

import java.util.*

fun interface RandomGenerator<out T> {
    fun generate(): T

    companion object {
        val uuid:  RandomGenerator<UUID> = RandomGenerator<UUID> { UUID.randomUUID() }
    }
}