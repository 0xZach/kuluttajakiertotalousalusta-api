package com.turku.routes


import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.turku.controllers.getProblemResults

fun Application.ResultRoute() {
    routing {
        getProblemResults()
    }
}


fun Route.getProblemResults() {
    get("/results/{problemId}/{latitude}/{longitude}") { getProblemResults(call) }
}
