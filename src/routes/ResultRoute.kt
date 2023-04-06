package com.turku.routes


import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.turku.controllers.getProblemResults
import com.turku.controllers.insertResult

fun Application.ResultRoute() {
    routing {
        getProblemResults()
        setResult()
    }
}


fun Route.getProblemResults() {
    get("/results/{problemId}/{latitude}/{longitude}") { getProblemResults(call) }
}

fun Route.setResult() {
    post("/results/insert") { insertResult(call) }
}