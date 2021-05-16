package com.ruchij.services.clock

import org.joda.time.DateTime

interface Clock {
    fun timestamp(): DateTime
}