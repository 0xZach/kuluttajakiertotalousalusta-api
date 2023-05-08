package com.turku.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.turku.controllers.getServiceTypes

fun Application.ServiceTypeRoute() {
    routing {
        getServiceTypes()
    }
}


fun Route.getServiceTypes() {
    get("/servicetypes") { getServiceTypes(call) }
}