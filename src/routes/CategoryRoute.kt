package com.turku.routes


import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.turku.controllers.getCategories

fun Application.CategoryRoute() {
    routing {
        getCategories()
    }
}


fun Route.getCategories() {
    get("/categories") { getCategories(call) }
}
