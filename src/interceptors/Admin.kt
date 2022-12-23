package com.turku.interceptors

import com.turku.common.respondUnAuthorized
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.admin(callback: Route.() -> Unit): Route {
    // With createChild, we create a child node for this received Route
    val childRouteNode = this.createChild(object : RouteSelector() {
        override fun evaluate(context: RoutingResolveContext, segmentIndex: Int): RouteSelectorEvaluation =
            RouteSelectorEvaluation.Constant
    })

    childRouteNode.intercept(ApplicationCallPipeline.Features) {
        val admin = call.request.headers["admin"]
        if (admin == "1") {
            proceed()
            return@intercept
        }
        call.respondUnAuthorized()
        finish()
    }

    // Configure this route with the block provided by the user
    callback(childRouteNode)

    return childRouteNode
}
