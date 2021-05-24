package com.ruchij.api.services.random

interface RandomGenerator<out T> {
    fun generate(): T
}