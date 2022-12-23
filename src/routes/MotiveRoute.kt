package com.turku.routes


import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.turku.controllers.getMotivesByCategory
import com.turku.controllers.updateHits

fun Application.MotiveRoute() {
    routing {
        getMotivesByCategory()
        updateHits()
    }
}


fun Route.getMotivesByCategory() {
    get("/motives/{category}") { getMotivesByCategory(call) }
}

fun Route.updateHits() {
    post("/update-hits") { updateHits(call) }
}
