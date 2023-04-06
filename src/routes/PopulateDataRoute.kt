package com.turku.routes

import com.turku.controllers.populateSheetData
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.PopulateDataRoute() {
    routing {
        populateSheetData()
    }
}

fun Route.populateSheetData() {
    post ("/populate-sheet-data") {
        populateSheetData(call)
    }
}
