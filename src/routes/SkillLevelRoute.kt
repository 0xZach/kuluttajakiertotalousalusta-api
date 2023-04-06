package com.turku.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.SkillLevelRoute() {
    routing {
        getSkillLevels()
    }
}


fun Route.getSkillLevels() {
    get("/skill-levels") {
        com.turku.controllers.getSkillLevels(call)
    }
}