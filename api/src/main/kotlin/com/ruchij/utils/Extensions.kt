package com.ruchij.utils

import java.util.*

object Extensions {

    fun <T> Optional<T>.nullableType(): T? = this.orElse(null)

    fun <T, R> T?.foldNullable(onNull: () -> R, onValue: (T) -> R): R = if (this == null) onNull() else onValue(this)

}