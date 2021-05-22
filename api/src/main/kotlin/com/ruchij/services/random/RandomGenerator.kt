package com.ruchij.services.random

interface RandomGenerator<out T> {
    fun generate(): T
}