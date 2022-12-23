package com.turku

import com.turku.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    // Initializations
    DatabaseFactory.init()

    // Plugins
    configureAuthentication()
    configureRouting()
    configureLocalization()
    configureMonitoring()
    configureHTTP()
    configureSerialization()
    configureScheduler()
}
