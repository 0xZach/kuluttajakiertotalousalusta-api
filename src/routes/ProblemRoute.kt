package com.turku.routes


import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.turku.controllers.getItemProblems

fun Application.ProblemRoute() {
    routing {
        getItemProblems()
    }
}


fun Route.getItemProblems() {
    get("/problems/{appItemId}") { getItemProblems(call) }
}
