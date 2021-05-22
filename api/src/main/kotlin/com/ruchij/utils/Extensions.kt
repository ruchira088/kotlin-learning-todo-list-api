package com.ruchij.utils

import java.util.*

object Extensions {

    fun <T> Optional<T>.nullableType(): T? = this.orElse(null)

}