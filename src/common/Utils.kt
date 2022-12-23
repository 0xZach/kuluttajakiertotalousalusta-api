package com.turku.common

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import java.time.LocalTime

class Utils {
    companion object {
        private val config = HoconApplicationConfig(ConfigFactory.load())

        fun getEnv(prop: String): String {
            return config.property(prop).getString()
        }

        fun getLocalTime(time: String): LocalTime {
            return LocalTime.parse(time)
        }

    }
}
