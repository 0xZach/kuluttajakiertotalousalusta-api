package com.turku.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.turku.controllers.getPostals
import com.turku.controllers.getPostalsByAddress
import io.ktor.server.response.*

fun Application.PostalRoute() {
    routing {
        /*
        getAllPostals()
        getSpecificPostals()
        */
        getPostal()
    }
}

fun Route.getPostal(){

    get ("/postal") {
        getPostals(call)
    }
    get ("/postal/{postalCode}/{streetName}") {
        getPostalsByAddress(call)
    }


}