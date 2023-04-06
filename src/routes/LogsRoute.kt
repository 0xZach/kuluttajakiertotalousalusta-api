package com.turku.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.turku.controllers.getLogs
import com.turku.controllers.setLogs

fun Application.LogsRoute() {
    routing {
        getLogs()
        setLogs()
    }
}


fun Route.getLogs() {
    get("/logs") { getLogs(call) }
}



// need post for 2 reasons:
// - restrain the possibility of someone sending information that could break the app/database
// - GET requests have trouble handling URLs in parameters.
fun Route.setLogs(){
    post("/logs/insert"){
        setLogs(call)
    }
}