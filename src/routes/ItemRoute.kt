package com.turku.routes


import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.turku.controllers.getItemsByCategory

fun Application.ItemRoute() {
    routing {
        getItemsByCategory()
    }
}


fun Route.getItemsByCategory() {
    get("/items/{appCategoryId}") { getItemsByCategory(call) }
}
