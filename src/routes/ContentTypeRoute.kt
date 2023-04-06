package com.turku.routes

import com.turku.controllers.getContentTypes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.ContentTypeRoute() {
    routing {
        getContentTypes()
    }
}


fun Route.getContentTypes() {
    get("/content-types") {
        getContentTypes(call)
    }
}