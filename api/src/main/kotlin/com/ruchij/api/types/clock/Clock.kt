package com.ruchij.api.types.clock

import org.joda.time.DateTime

fun interface Clock {
    fun timestamp(): DateTime
}