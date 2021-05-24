package com.ruchij.api.types.clock

import org.joda.time.DateTime

interface Clock {
    fun timestamp(): DateTime
}