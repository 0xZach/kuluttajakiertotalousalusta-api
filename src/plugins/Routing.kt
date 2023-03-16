package com.turku.plugins

import com.turku.common.ApplicationLogger
import com.turku.interceptors.admin
import com.github.aymanizz.ktori18n.R
import com.github.aymanizz.ktori18n.t
import com.turku.routes.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    MotiveRoute()
    CategoryRoute()
    ItemRoute()
    ProblemRoute()
    ResultRoute()
    LogsRoute()
    PostalRoute()
    PopulateDataRoute()
    routing {
        get("/health-check") {
            ApplicationLogger.api("HELLO")
            call.respondText(call.t(R("HELLO")))
        }
        admin {
            get("/") {
                ApplicationLogger.api("HELLO")
                call.respondText(call.t(R("HELLO")))
            }
        }
    }
}
