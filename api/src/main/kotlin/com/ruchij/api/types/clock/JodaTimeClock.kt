package com.ruchij.api.types.clock

import org.joda.time.DateTime

object JodaTimeClock: Clock {
    override fun timestamp(): DateTime = DateTime.now()
}